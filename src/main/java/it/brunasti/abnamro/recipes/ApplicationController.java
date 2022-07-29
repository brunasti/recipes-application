package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.db.Ingredient;
import it.brunasti.abnamro.recipes.db.Recipe;
import it.brunasti.abnamro.recipes.requests.RecipeRequest;
import it.brunasti.abnamro.recipes.responses.RecipeResponse;
import it.brunasti.abnamro.recipes.responses.RecipesListResponse;
import it.brunasti.abnamro.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("")
class ApplicationController {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	private final RecipeService recipeService;

	public ApplicationController(RecipeService recipeService) {
		logger.info("ApplicationController created");
		this.recipeService = recipeService;
	}

	@GetMapping("/recipes/{id}")
	EntityModel<RecipeResponse> retieveOneRecipe(@PathVariable Long id, HttpServletRequest request) {
		return recipeService.retrieveRecipe(id, Utils.extractToken(request));
	}

	@GetMapping("/recipes")
	EntityModel<RecipesListResponse> retrieveRecipes(
			HttpServletRequest request,
			@RequestParam(required = false) Boolean vegetarian,
			@RequestParam(required = false) String instructions,
			@RequestParam(required = false) String[] includeIngredients,
			@RequestParam(required = false) String[] excludeIngredients,
			@RequestParam(required = false) Integer servings
	) {
		logger.info("retrieveRecipes");
		logger.info("retrieveRecipes vegetarian : ["+vegetarian+"]");
		logger.info("retrieveRecipes instructions : ["+instructions+"]");
		logger.info("retrieveRecipes includeIngredients : ["+Utils.arrayDump(includeIngredients)+"]");
		logger.info("retrieveRecipes excludeIngredients : ["+Utils.arrayDump(excludeIngredients)+"]");
		logger.info("retrieveRecipes servings : ["+servings+"]");
		return recipeService.retrieveRecipes(Utils.extractToken(request),
				vegetarian, instructions, includeIngredients, excludeIngredients, servings);
	}

	@DeleteMapping("/recipes/{id}")
	EntityModel<RecipeResponse> deleteRecipe(@PathVariable Long id, HttpServletRequest request) {
		logger.info("deleteRecipe : " + id);
		return recipeService.deleteRecipe(id, Utils.extractToken(request));
	}

	@PostMapping("/recipes")
	EntityModel<RecipeResponse> createRecipe(@RequestBody RecipeRequest newRecipe, HttpServletRequest request) {
		logger.info("createRecipe");
		return recipeService.createRecipe(newRecipe, Utils.extractToken(request));
	}

	@PutMapping("/recipes/{id}")
	EntityModel<RecipeResponse> updateRecipe(@PathVariable Long id, @RequestBody RecipeRequest newRecipe, HttpServletRequest request) {
		logger.info("updateRecipe : " + id);
		return recipeService.updateRecipe(id, newRecipe, Utils.extractToken(request));
	}




	@GetMapping({ "/public" })
	public String publicEndpoint() {
		return "Public Endpoint Response";
	}

	@GetMapping({ "/protected" })
	public String protectedEndpoint(HttpServletRequest request) {
		logger.info("protectedEndpoint Start");

		String token = Utils.extractToken(request);
		logger.info("protectedEndpoint token : ["+token+"]");

		return "Access Token :<br><b>[ "+token+" ]</b>";
	}

}
