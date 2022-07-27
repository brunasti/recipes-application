package it.brunasti.abnamro.recipes.exceptions;

public class ApplicationUserNotFoundException extends RuntimeException {

	public ApplicationUserNotFoundException(Long id) {
		super("Could not find Application User " + id);
	}
}
