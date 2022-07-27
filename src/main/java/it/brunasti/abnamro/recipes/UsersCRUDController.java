package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.db.ApplicationUser;
import it.brunasti.abnamro.recipes.db.ApplicationUserReporitory;
import it.brunasti.abnamro.recipes.exceptions.ApplicationUserNotFoundException;
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
class UsersCRUDController {

	private final ApplicationUserReporitory repository;

	UsersCRUDController(ApplicationUserReporitory repository) {
		this.repository = repository;
	}

	// Aggregate root

	// tag::get-aggregate-root[]
//	@RequestMapping(value = "", method= RequestMethod.GET)
	@GetMapping("/crud/application_users")
	CollectionModel<EntityModel<ApplicationUser>> all() {
		List<EntityModel<ApplicationUser>> applicationUsers = repository.findAll().stream()
				.map(EntityModel::of)
				.collect(Collectors.toList());
		return CollectionModel.of(applicationUsers);
	}
	// end::get-aggregate-root[]

	@PostMapping("/crud/application_users")
	ApplicationUser newApplicationUser(@RequestBody ApplicationUser newApplicationUser) {
		return repository.save(newApplicationUser);
	}

	// Single item

	// tag::get-single-item[]
	@GetMapping("/crud/application_users/{id}")
	EntityModel<ApplicationUser> one(@PathVariable Long id) {

		ApplicationUser applicationUser = repository.findById(id) //
				.orElseThrow(() -> new ApplicationUserNotFoundException(id));

		return EntityModel.of(applicationUser);
	}
	// end::get-single-item[]

	@PutMapping("/crud/application_users/{id}")
	ApplicationUser replaceApplicationUser(@RequestBody ApplicationUser newApplicationUser, @PathVariable Long id) {

		return repository.findById(id) //
				.map(applicationUser -> {
					applicationUser.setName(newApplicationUser.getName());
					return repository.save(applicationUser);
				}) //
				.orElseGet(() -> {
					newApplicationUser.setId(id);
					return repository.save(newApplicationUser);
				});
	}

	@DeleteMapping("/crud/application_users/{id}")
	void deleteApplicationUser(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
