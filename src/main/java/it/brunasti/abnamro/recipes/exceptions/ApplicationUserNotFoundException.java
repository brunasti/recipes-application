package it.brunasti.abnamro.recipes.exceptions;

public class ApplicationUserNotFoundException extends RuntimeException {

	public ApplicationUserNotFoundException(String id) {
		super("Could not find Application User " + id);
	}
}
