package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.requests.RecipeRequest;
import it.brunasti.abnamro.recipes.responses.RecipeResponse;
import it.brunasti.abnamro.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	EntityModel<RecipeResponse> retieveOneRecipe(@PathVariable Long id) {
		logger.info("retieveOneRecipe : " + id);
		return recipeService.retrieveRecipe(id);
	}

	@DeleteMapping("/recipes/{id}")
	EntityModel<RecipeResponse> deleteRecipe(@PathVariable Long id) {
		logger.info("deleteRecipe : " + id);
		return recipeService.deleteRecipe(id);
	}

	@PostMapping("/recipes")
	EntityModel<RecipeResponse> createRecipe(@RequestBody RecipeRequest newRecipe) {
		logger.info("createRecipe");
		return recipeService.createRecipe(newRecipe);
	}

	@PutMapping("/recipes/{id}")
	EntityModel<RecipeResponse> updateRecipe(@PathVariable Long id, @RequestBody RecipeRequest newRecipe) {
		logger.info("updateRecipe : " + id);
		return recipeService.updateRecipe(id, newRecipe);
	}


}
