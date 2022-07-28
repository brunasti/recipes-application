package it.brunasti.abnamro.recipes.exceptions;

public class RecipeMismatchException extends RuntimeException {

	public RecipeMismatchException(String name, String username, Long foundId, Long searchedId) {
		super("Recipe with name : " + name + " for username : " + username + " has id ["+foundId+"] instead of ["+searchedId+"]");
	}
}
