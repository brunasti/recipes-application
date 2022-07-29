package it.brunasti.abnamro.recipes.controllers;

import it.brunasti.abnamro.recipes.db.RecipeIngredientRelation;
import it.brunasti.abnamro.recipes.db.RecipeIngredientRelationRepository;
import it.brunasti.abnamro.recipes.exceptions.RecipeIngredientRelationNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// tag::hateoas-imports[]
// end::hateoas-imports[]

@RestController
@RequestMapping("")
class RecipeIngredientRelationsCRUDController {

	private final RecipeIngredientRelationRepository repository;

	RecipeIngredientRelationsCRUDController(RecipeIngredientRelationRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
	@GetMapping("/crud/recipe_ingredient_relations")
	CollectionModel<EntityModel<RecipeIngredientRelation>> all() {
		List<EntityModel<RecipeIngredientRelation>> recipeIngredientRelations = repository.findAll().stream()
				.map(EntityModel::of)
				.collect(Collectors.toList());
		return CollectionModel.of(recipeIngredientRelations);
	}
	// end::get-aggregate-root[]

	@PostMapping("/crud/recipe_ingredient_relations")
	RecipeIngredientRelation newRecipeIngredientRelation(@RequestBody RecipeIngredientRelation newRecipeIngredientRelation) {
		return repository.save(newRecipeIngredientRelation);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/crud/recipe_ingredient_relations/{id}")
	EntityModel<RecipeIngredientRelation> one(@PathVariable Long id) {

		RecipeIngredientRelation recipeIngredientRelation = repository.findById(id) //
				.orElseThrow(() -> new RecipeIngredientRelationNotFoundException(id));

		return EntityModel.of(recipeIngredientRelation);
	}
	// end::get-single-item[]

	@PutMapping("/crud/recipe_ingredient_relations/{id}")
	RecipeIngredientRelation replaceRecipeIngredientRelation(@RequestBody RecipeIngredientRelation newRecipeIngredientRelation, @PathVariable Long id) {

		return repository.findById(id) //
				.map(recipeIngredientRelation -> {
					recipeIngredientRelation.setQuantity(newRecipeIngredientRelation.getQuantity());
					return repository.save(recipeIngredientRelation);
				}) //
				.orElseGet(() -> {
					newRecipeIngredientRelation.setId(id);
					return repository.save(newRecipeIngredientRelation);
				});
	}

	@DeleteMapping("/crud/recipe_ingredient_relations/{id}")
	void deleteRecipeIngredientRelation(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
