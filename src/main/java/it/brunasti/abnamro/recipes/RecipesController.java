package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.db.*;

import it.brunasti.abnamro.recipes.exceptions.RecipeNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
class RecipesController {

	private final RecipeRepository repository;

	RecipesController(RecipeRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/recipes")
	CollectionModel<EntityModel<Recipe>> all() {
		List<EntityModel<Recipe>> recipes = repository.findAll().stream()
				.map(EntityModel::of)
				.collect(Collectors.toList());
		return CollectionModel.of(recipes);
	}
	// end::get-aggregate-root[]

	@PostMapping("/recipes")
	Recipe newEmployee(@RequestBody Recipe newEmployee) {
		return repository.save(newEmployee);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/recipes/{id}")
	EntityModel<Recipe> one(@PathVariable Long id) {

		Recipe recipe = repository.findById(id) //
				.orElseThrow(() -> new RecipeNotFoundException(id));

		return EntityModel.of(recipe);
	}
	// end::get-single-item[]

	@PutMapping("/recipes/{id}")
	Recipe replaceEmployee(@RequestBody Recipe newRecipe, @PathVariable Long id) {

		return repository.findById(id) //
				.map(recipe -> {
					recipe.setName(newRecipe.getName());
					return repository.save(recipe);
				}) //
				.orElseGet(() -> {
					newRecipe.setId(id);
					return repository.save(newRecipe);
				});
	}

	@DeleteMapping("/recipes/{id}")
	void deleteEmployee(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
