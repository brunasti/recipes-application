package it.brunasti.abnamro.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

	// TODO :
	// DONE - execute protected APIs with token
	// DONE - rewrite UserServiceImpl to use the DAO classes and tables
	// DONE - check user access to recipes not owned by him/her
	// DONE - query by example search
		//1. If the dish is vegetarian or not - OK
		//2. The number of servings - OK
		//3. Specific ingredients (either include or exclude) - OK
		//4. Text search within the instructions. - OK
	// - add API input validations as RequestParam
		// 1.ExceptionHandler
	// - add more unit tests for coverage
	// - add integration tests
	// DONE - document how to start
	// DONE - document how to use the token in Postman
	// - document API calls and parameters
	// - define the error responses
	// DONE - clean code

	public static void main(String[] args) {

		SpringApplication.run(RecipesApplication.class, args);

	}
}
