package it.brunasti.abnamro.recipes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {

	// TODO :
	// - add API input validations as RequestParam
	// - document API calls and parameters
	// - rewrite UserServiceImpl to match and use the DAO classes and tables
	// - add more unit tests for coverage
	// - add integration tests

	public static void main(String[] args) {

		SpringApplication.run(RecipesApplication.class, args);

	}
}
