package it.brunasti.abnamro.recipes.exceptions;

public class RecipeNotFoundException extends RuntimeException {

	public RecipeNotFoundException(Long id) {
		super("Could not find recipe with id : " + id);
	}

	public RecipeNotFoundException(String name, String username) {
		super("Could not find recipe with name : " + name + " for username : " + username);
	}
}
