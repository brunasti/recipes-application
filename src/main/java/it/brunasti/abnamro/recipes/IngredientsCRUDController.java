package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.db.Ingredient;
import it.brunasti.abnamro.recipes.db.IngredientRepository;
import it.brunasti.abnamro.recipes.exceptions.IngredienNotFoundException;
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
class IngredientsCRUDController {

	private final IngredientRepository repository;

	IngredientsCRUDController(IngredientRepository repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
//	@RequestMapping(value = "", method= RequestMethod.GET)
	@GetMapping("/crud/ingredients")
	CollectionModel<EntityModel<Ingredient>> all() {
		List<EntityModel<Ingredient>> ingredients = repository.findAll().stream()
				.map(EntityModel::of)
				.collect(Collectors.toList());
		return CollectionModel.of(ingredients);
	}
	// end::get-aggregate-root[]

	@PostMapping("/crud/ingredients")
	Ingredient newIngredient(@RequestBody Ingredient newIngredient) {
		return repository.save(newIngredient);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/crud/ingredients/{id}")
	EntityModel<Ingredient> one(@PathVariable Long id) {

		Ingredient ingredient = repository.findById(id) //
				.orElseThrow(() -> new IngredienNotFoundException(id));

		return EntityModel.of(ingredient);
	}
	// end::get-single-item[]

	@PutMapping("/crud/ingredients/{id}")
	Ingredient replaceIngredient(@RequestBody Ingredient newIngredient, @PathVariable Long id) {

		return repository.findById(id) //
				.map(ingredient -> {
					ingredient.setName(newIngredient.getName());
					return repository.save(ingredient);
				}) //
				.orElseGet(() -> {
					newIngredient.setId(id);
					return repository.save(newIngredient);
				});
	}

	@DeleteMapping("/crud/ingredients/{id}")
	void deleteIngredient(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
