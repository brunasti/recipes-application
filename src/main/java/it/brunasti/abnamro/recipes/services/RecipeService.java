package it.brunasti.abnamro.recipes.services;

import it.brunasti.abnamro.recipes.db.Ingredient;
import it.brunasti.abnamro.recipes.db.IngredientRepository;
import it.brunasti.abnamro.recipes.db.Recipe;
import it.brunasti.abnamro.recipes.db.RecipeIngredientRelation;
import it.brunasti.abnamro.recipes.db.RecipeIngredientRelationRepository;
import it.brunasti.abnamro.recipes.db.RecipeRepository;
import it.brunasti.abnamro.recipes.exceptions.RecipeAlreadyExistsException;
import it.brunasti.abnamro.recipes.exceptions.RecipeNotFoundException;
import it.brunasti.abnamro.recipes.requests.NewRecipeRequest;
import it.brunasti.abnamro.recipes.responses.RecipeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRelationRepository recipeIngredientRelationRepository;

    private static final Logger logger = LoggerFactory.getLogger(RecipeService.class);


    public EntityModel<RecipeResponse> retrieveRecipe(Long id) {
        logger.info("retrieveRecipe "+id);

        Recipe recipe = recipeRepository.findById(id) //
                .orElseThrow(() -> new RecipeNotFoundException(id));

        Map<RecipeIngredientRelation, Ingredient> relation = new HashMap<>();

        List<RecipeIngredientRelation> recipeIngredientRelationList = recipeIngredientRelationRepository.findByRecipeId(id);
        recipeIngredientRelationList.forEach(recipeIngredientRelation -> {
            Optional<Ingredient> ingredient = ingredientRepository.findById(recipeIngredientRelation.getIngredientId());
            ingredient.ifPresent(value -> relation.put(recipeIngredientRelation, value));
        });

        RecipeResponse recipeResponse = RecipeResponse.createFromRecipe(recipe, relation);

        logger.info("retrieveRecipe "+id+" - END");

        return EntityModel.of(recipeResponse);
    }

    public EntityModel<RecipeResponse> createRecipe(NewRecipeRequest newRecipe, String username) {
        logger.info("createRecipe : " + newRecipe.getName() + " - " + username );
        logger.info("createRecipe : [" + newRecipe + "]" );
        Optional<Recipe> recipeOptional = recipeRepository.findByNameAndOwnerId(newRecipe.getName(), username);
        if (recipeOptional.isPresent()) {
            logger.info("createRecipe - already FOUND ");
            throw new RecipeAlreadyExistsException(newRecipe.getName(), newRecipe.getOwnerId());
        }

        logger.info("createRecipe - Create Recipe");
        Recipe recipe = newRecipe.toRecipe();
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

        RecipeResponse recipeResponse = RecipeResponse.createFromRecipe(recipe, relation);

        logger.info("createRecipe - END");
        return EntityModel.of(recipeResponse);
    }

    public EntityModel<RecipeResponse>  deleteRecipe(@PathVariable Long id) {
        EntityModel<RecipeResponse> recipeResponseEntityModel = retrieveRecipe(id);
        recipeRepository.deleteById(id);
        return recipeResponseEntityModel;
    }

}
