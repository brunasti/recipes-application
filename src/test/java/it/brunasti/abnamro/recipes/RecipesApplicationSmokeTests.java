package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.controllers.ApplicationController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecipesApplicationSmokeTests {

	@Autowired
	private ApplicationController controller;

	@Autowired
	private RecipesApplication recipesApplication;

	@Test
	public void contextLoads() {
		assertThat(controller).isNotNull();
		assertThat(recipesApplication).isNotNull();
	}

}
