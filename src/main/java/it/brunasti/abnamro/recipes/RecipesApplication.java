package it.brunasti.abnamro.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

	// TODO :
	// DONE - execute protected APIs with token
	// - rewrite UserServiceImpl to use the DAO classes and tables
	// - check user access to recipes not owned by him/her
	// - add API input validations as RequestParam
	// - document API calls and parameters
	// - add more unit tests for coverage
	// - add integration tests
	// - document how to use the token in Postman

	public static void main(String[] args) {

		SpringApplication.run(RecipesApplication.class, args);

	}
}
