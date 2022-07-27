package it.brunasti.abnamro.recipes.exceptions;

public class RecipeIngredientRelationNotFoundException extends RuntimeException {

	public RecipeIngredientRelationNotFoundException(Long id) {
		super("Could not find Recipe Ingredient Relation " + id);
	}
}
