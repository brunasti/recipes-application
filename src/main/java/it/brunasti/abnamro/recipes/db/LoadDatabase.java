package it.brunasti.abnamro.recipes.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

	@Bean
	CommandLineRunner initDatabase(ApplicationUserReporitory applicationUserReporitory, RecipeRepository recipeRepository, IngredientRepository ingredientRepository, RecipeIngredientRelationRepository recipeIngredientRelationRepository) {

		return args -> {
			String userId = "paolo";
			ApplicationUser user = ApplicationUser.builder().id(userId).username("Paolo").password("Brunasti").build();
			log.info("Preloading " + applicationUserReporitory.save(user));

			Recipe spaghettiAlBurro = Recipe.builder().ownerId(user.getId()).name("spaghetti al burro").instructions("Boil water").servings(2).build();
			log.info("Preloading " + recipeRepository.save(spaghettiAlBurro));

			Ingredient spaghetti = Ingredient.builder().name("spaghetti").description("spaghetti di grano duro").vegetarian(true).unit("g").build();
			Ingredient burro = Ingredient.builder().name("burro").description("burro di latte vaccino").vegetarian(false).unit("g").build();
			Ingredient acqua = Ingredient.builder().name("acqua").description("H2O").vegetarian(true).unit("ml").build();
			Ingredient sale = Ingredient.builder().name("sale").description("NaCl").vegetarian(true).unit("g").build();

			log.info("Preloading " + ingredientRepository.save(spaghetti));
			log.info("Preloading " + ingredientRepository.save(burro));
			log.info("Preloading " + ingredientRepository.save(acqua));
			log.info("Preloading " + ingredientRepository.save(sale));

			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiAlBurro.getId()).ingredientId(spaghetti.getId()).quantity(BigDecimal.valueOf(100)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiAlBurro.getId()).ingredientId(burro.getId()).quantity(BigDecimal.valueOf(20)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiAlBurro.getId()).ingredientId(acqua.getId()).quantity(BigDecimal.valueOf(2000)).build()));
			log.info("Preloading " + recipeIngredientRelationRepository.save(
					RecipeIngredientRelation.builder().recipeId(spaghettiAlBurro.getId()).ingredientId(sale.getId()).quantity(BigDecimal.valueOf(10)).build()));

		};
	}
}
