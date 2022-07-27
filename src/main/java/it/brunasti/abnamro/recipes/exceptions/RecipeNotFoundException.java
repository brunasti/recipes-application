package it.brunasti.abnamro.recipes.exceptions;

public class RecipeNotFoundException extends RuntimeException {

	public RecipeNotFoundException(Long id) {
		super("Could not find recipe " + id);
	}
}
