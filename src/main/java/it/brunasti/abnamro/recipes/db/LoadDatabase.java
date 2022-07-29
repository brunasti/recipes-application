package it.brunasti.abnamro.recipes.db;

import it.brunasti.abnamro.recipes.jwt.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Autowired
	UserService users;

	@Bean
	CommandLineRunner initDatabase(ApplicationUserReporitory applicationUserReporitory, RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeIngredientRelationRepository recipeIngredientRelationRepository) {

		return args -> {
			String userId = "paolo";
			ApplicationUser user = ApplicationUser.builder().id(userId).username("paolo").password("brunasti").build();
			log.info("Preloading " + applicationUserReporitory.save(user));
			users.save(user);

			Recipe spaghettiAlBurro = Recipe.builder().ownerId(user.getId()).name("spaghetti al burro").instructions("Boil water").servings(2).build();
			log.info("Preloading " + recipeRepository.save(spaghettiAlBurro));

			Recipe spaghettiOlio = Recipe.builder().ownerId(user.getId()).name("spaghetti all'olio").instructions("Boil water").servings(3).build();
			log.info("Preloading " + recipeRepository.save(spaghettiOlio));

			Recipe crostini = Recipe.builder().ownerId(user.getId()).name("crostini").instructions("toast bread").servings(4).build();
			log.info("Preloading " + recipeRepository.save(crostini));

			Ingredient spaghetti = Ingredient.builder().name("spaghetti").description("spaghetti di grano duro").vegetarian(true).unit("g").build();
			Ingredient burro = Ingredient.builder().name("burro").description("burro di latte vaccino").vegetarian(false).unit("g").build();
			Ingredient acqua = Ingredient.builder().name("acqua").description("H2O").vegetarian(true).unit("ml").build();
			Ingredient sale = Ingredient.builder().name("sale").description("NaCl").vegetarian(true).unit("g").build();
			Ingredient olio = Ingredient.builder().name("olio").description("olio di olive").vegetarian(true).unit("ml").build();
			Ingredient pane = Ingredient.builder().name("pane").description("pane").vegetarian(true).unit("slices").build();

			log.info("Preloading " + ingredientRepository.save(spaghetti));
			log.info("Preloading " + ingredientRepository.save(burro));
			log.info("Preloading " + ingredientRepository.save(acqua));
			log.info("Preloading " + ingredientRepository.save(sale));
			log.info("Preloading " + ingredientRepository.save(olio));
			log.info("Preloading " + ingredientRepository.save(pane));

			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiOlio.getId()).ingredientId(spaghetti.getId()).quantity(BigDecimal.valueOf(150)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiOlio.getId()).ingredientId(olio.getId()).quantity(BigDecimal.valueOf(20)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiOlio.getId()).ingredientId(acqua.getId()).quantity(BigDecimal.valueOf(3000)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiOlio.getId()).ingredientId(sale.getId()).quantity(BigDecimal.valueOf(15)).build()));

			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiAlBurro.getId()).ingredientId(spaghetti.getId()).quantity(BigDecimal.valueOf(100)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiAlBurro.getId()).ingredientId(burro.getId()).quantity(BigDecimal.valueOf(20)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiAlBurro.getId()).ingredientId(acqua.getId()).quantity(BigDecimal.valueOf(2000)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiAlBurro.getId()).ingredientId(sale.getId()).quantity(BigDecimal.valueOf(10)).build()));

			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(crostini.getId()).ingredientId(pane.getId()).quantity(BigDecimal.valueOf(4)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(crostini.getId()).ingredientId(olio.getId()).quantity(BigDecimal.valueOf(10)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(crostini.getId()).ingredientId(sale.getId()).quantity(BigDecimal.valueOf(4)).build()));

			// add one more user to check access to recipes from another user
			user = ApplicationUser.builder().id("mario").username("mario").password("bross").build();
			log.info("Preloading " + applicationUserReporitory.save(user));
			users.save(user);
		};
	}
}
