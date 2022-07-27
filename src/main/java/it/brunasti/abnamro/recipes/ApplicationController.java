package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.responses.RecipeResponse;
import it.brunasti.abnamro.recipes.services.RecipeService;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
class ApplicationController {

	private final RecipeService recipeService;

	public ApplicationController(
			RecipeService recipeService
	) {
		this.recipeService = recipeService;
	}

	@GetMapping("/recipes/{id}")
	EntityModel<RecipeResponse> oneRecipe(@PathVariable Long id) {
		return recipeService.retrieveRecipe(id);
	}

}
