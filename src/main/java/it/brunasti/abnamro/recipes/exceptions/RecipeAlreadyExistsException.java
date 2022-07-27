package it.brunasti.abnamro.recipes.exceptions;

public class RecipeAlreadyExistsException extends RuntimeException {

	public RecipeAlreadyExistsException(String name, String username) {
		super("Recipe with name : " + name + " for username : " + username + " already exists");
	}
}
