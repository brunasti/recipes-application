package it.brunasti.abnamro.recipes.exceptions;

public class IngredienNotFoundException extends RuntimeException {

	public IngredienNotFoundException(Long id) {
		super("Could not find Ingredient " + id);
	}
}
