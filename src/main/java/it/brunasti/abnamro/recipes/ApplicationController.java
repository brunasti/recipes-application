package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.db.Recipe;
import it.brunasti.abnamro.recipes.requests.NewRecipeRequest;
import it.brunasti.abnamro.recipes.responses.RecipeResponse;
import it.brunasti.abnamro.recipes.services.RecipeService;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
class ApplicationController {

	private final RecipeService recipeService;

	public ApplicationController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/recipes/{id}")
	EntityModel<RecipeResponse> retieveOneRecipe(@PathVariable Long id) {
		return recipeService.retrieveRecipe(id);
	}

	@DeleteMapping("/recipes/{id}")
	EntityModel<RecipeResponse> deleteRecipe(@PathVariable Long id) {
		return recipeService.deleteRecipe(id);
	}

	@PostMapping("/recipes")
	EntityModel<RecipeResponse> createRecipe(@RequestBody NewRecipeRequest newRecipe) {
		return recipeService.createRecipe(newRecipe,"paolo");
	}


}
