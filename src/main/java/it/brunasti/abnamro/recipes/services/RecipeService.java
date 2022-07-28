package it.brunasti.abnamro.recipes.services;

import it.brunasti.abnamro.recipes.db.ApplicationUser;
import it.brunasti.abnamro.recipes.db.Ingredient;
import it.brunasti.abnamro.recipes.db.IngredientRepository;
import it.brunasti.abnamro.recipes.db.Recipe;
import it.brunasti.abnamro.recipes.db.RecipeIngredientRelation;
import it.brunasti.abnamro.recipes.db.RecipeIngredientRelationRepository;
import it.brunasti.abnamro.recipes.db.RecipeRepository;
import it.brunasti.abnamro.recipes.exceptions.RecipeAlreadyExistsException;
import it.brunasti.abnamro.recipes.exceptions.RecipeMismatchException;
import it.brunasti.abnamro.recipes.exceptions.RecipeNotFoundException;
import it.brunasti.abnamro.recipes.jwt.TokenAuthenticationService;
import it.brunasti.abnamro.recipes.requests.RecipeRequest;
import it.brunasti.abnamro.recipes.responses.RecipeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.PathVariable;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.NotAuthorizedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Singleton
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RecipeService {
    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRelationRepository recipeIngredientRelationRepository;
	private final TokenAuthenticationService tokenAuthenticationService;


    public EntityModel<RecipeResponse> retrieveRecipe(Long id, String token) {
        logger.info("retrieveRecipe "+id);
        logger.info("retrieveRecipe token : ["+token+"]");

        // Retrieve the recipe
        Recipe recipe = recipeRepository.findById(id) //
                .orElseThrow(() -> new RecipeNotFoundException(id));

        // Check if recipe owner corresponds to token user
        Optional<ApplicationUser> applicationUser = tokenAuthenticationService.findByToken(token);
        if (applicationUser.isEmpty()) {
            logger.info("retrieveRecipe User Not found");
            throw new RecipeNotFoundException(id);
        }
        if (!applicationUser.get().getId().equals(recipe.getOwnerId())) {
            logger.info("retrieveRecipe User Not Match : ["+applicationUser.get().getUsername()+"] ["+recipe.getOwnerId()+"]");
            throw new RecipeNotFoundException(id);
        }

        // Build all the recipe structure with sub relations
        Map<RecipeIngredientRelation, Ingredient> relation = new HashMap<>();

        List<RecipeIngredientRelation> recipeIngredientRelationList = recipeIngredientRelationRepository.findByRecipeId(id);
        recipeIngredientRelationList.forEach(recipeIngredientRelation -> {
            Optional<Ingredient> ingredient = ingredientRepository.findById(recipeIngredientRelation.getIngredientId());
            ingredient.ifPresent(value -> relation.put(recipeIngredientRelation, value));
        });

        logger.info("retrieveRecipe "+id+" - END");

        return EntityModel.of(RecipeResponse.createFromRecipe(recipe, relation));
    }


    public EntityModel<RecipeResponse> createRecipe(RecipeRequest newRecipe, String token) {
        Optional<ApplicationUser> applicationUser = tokenAuthenticationService.findByToken(token);
        if (applicationUser.isEmpty()) {
            logger.info("createRecipe User Not found");
            throw new NotAuthorizedException("createRecipe User Not found");
        }

        logger.info("createRecipe : " + newRecipe.getName() + " - " + applicationUser.get().getId() );
        logger.info("createRecipe : [" + newRecipe + "]" );
        Optional<Recipe> recipeOptional = recipeRepository.findByNameAndOwnerId(newRecipe.getName(), applicationUser.get().getId());
        if (recipeOptional.isPresent()) {
            logger.info("createRecipe - already FOUND ");
            throw new RecipeAlreadyExistsException(newRecipe.getName(), applicationUser.get().getId());
        }

        logger.info("createRecipe - Create Recipe");
        Recipe recipe = newRecipe.toRecipe(applicationUser.get().getId());
        recipeRepository.save(recipe);

        Map<RecipeIngredientRelation, Ingredient> relation = new HashMap<>();

        newRecipe.getIngredients().forEach(newIngredientRequest -> {
            Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(newIngredientRequest.getName());
            Ingredient ingredient;
            if (ingredientOptional.isPresent()) {
                ingredient = ingredientOptional.get();
            } else {
                ingredient = newIngredientRequest.toIngredent();
                ingredientRepository.save(ingredient);
            }

            RecipeIngredientRelation recipeIngredientRelation =
                    RecipeIngredientRelation.builder()
                            .recipeId(recipe.getId())
                            .ingredientId(ingredient.getId())
                            .quantity(newIngredientRequest.getQuantity())
                            .build();
            recipeIngredientRelationRepository.save(recipeIngredientRelation);

            relation.put(recipeIngredientRelation, ingredient);
        });

//        RecipeResponse recipeResponse = RecipeResponse.createFromRecipe(recipe, relation);

        logger.info("createRecipe - END");
        return EntityModel.of(RecipeResponse.createFromRecipe(recipe, relation));
    }

    @Transactional
    public EntityModel<RecipeResponse> updateRecipe(Long id, RecipeRequest recipeRequest, String token) {
        Optional<ApplicationUser> applicationUser = tokenAuthenticationService.findByToken(token);
        if (applicationUser.isEmpty()) {
            logger.info("createRecipe User Not found");
            throw new NotAuthorizedException("createRecipe User Not found");
        }

        logger.info("updateRecipe : [" + id + "] - " + recipeRequest.getName() + " - " + applicationUser.get().getId() );
        logger.info("updateRecipe : [" + recipeRequest + "]" );
        Optional<Recipe> recipeOptional = recipeRepository.findByNameAndOwnerId(recipeRequest.getName(), applicationUser.get().getId());
        if (recipeOptional.isEmpty()) {
            logger.info("updateRecipe - Not FOUND ");
            throw new RecipeNotFoundException(recipeRequest.getName(), applicationUser.get().getId());
        }

        if (!recipeOptional.get().getId().equals(id)) {
            logger.info("updateRecipe - id Mismatch id ["+recipeOptional.get().getId()+"] instead of ["+id+"]");
            throw new RecipeMismatchException(recipeRequest.getName(), applicationUser.get().getId(), recipeOptional.get().getId(), id);
        }

        logger.info("updateRecipe - Save Recipe");
        Recipe recipe = recipeRequest.toRecipe(applicationUser.get().getId());
        recipe.setId(id);
        recipeRepository.save(recipe);

        logger.info("updateRecipe - Delete all the relations for this recipe");
        recipeIngredientRelationRepository.deleteByRecipeId(recipe.getId());

        logger.info("updateRecipe - Recreate the relations for this recipe");
        Map<RecipeIngredientRelation, Ingredient> relation = new HashMap<>();

        recipeRequest.getIngredients().forEach(newIngredientRequest -> {
            Optional<Ingredient> ingredientOptional = ingredientRepository.findByName(newIngredientRequest.getName());
            Ingredient ingredient = ingredientOptional.orElseGet(newIngredientRequest::toIngredent);
            ingredientRepository.save(ingredient);

            RecipeIngredientRelation recipeIngredientRelation =
                    RecipeIngredientRelation.builder()
                            .recipeId(recipe.getId())
                            .ingredientId(ingredient.getId())
                            .quantity(newIngredientRequest.getQuantity())
                            .build();
            recipeIngredientRelationRepository.save(recipeIngredientRelation);

            relation.put(recipeIngredientRelation, ingredient);
        });

//        logger.info("updateRecipe - create response");
//        RecipeResponse recipeResponse = RecipeResponse.createFromRecipe(recipe, relation);

        logger.info("updateRecipe - END");
        return EntityModel.of(RecipeResponse.createFromRecipe(recipe, relation));
    }

    public EntityModel<RecipeResponse>  deleteRecipe(Long id, String token) {
        logger.info("deleteRecipe "+id);
        logger.info("deleteRecipe token : ["+token+"]");
        EntityModel<RecipeResponse> recipeResponseEntityModel = retrieveRecipe(id, token);
        recipeRepository.deleteById(id);
        logger.info("deleteRecipe - END");
        return recipeResponseEntityModel;
    }

}
