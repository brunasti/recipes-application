package it.brunasti.abnamro.recipes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = { EmptyTestConfig.class })
class RecipesApplicationTests {

	@Test
	void contextLoads() {
	}

}
