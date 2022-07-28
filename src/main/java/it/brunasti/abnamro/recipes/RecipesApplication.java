package it.brunasti.abnamro.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

	// TODO :
	// DONE - execute protected APIs with token
	// DONE - rewrite UserServiceImpl to use the DAO classes and tables
	// DONE - check user access to recipes not owned by him/her
	// - query by example search
		//1. Whether or not the dish is vegetarian - OK
		//2. The number of servings - OK
		//3. Specific ingredients (either include or exclude)
		//4. Text search within the instructions.
	// - add API input validations as RequestParam
	// - add more unit tests for coverage
	// - add integration tests
	// - document how to start
	// - document how to use the token in Postman
	// - document API calls and parameters
	// - define the error responses

	public static void main(String[] args) {

		SpringApplication.run(RecipesApplication.class, args);

	}
}
