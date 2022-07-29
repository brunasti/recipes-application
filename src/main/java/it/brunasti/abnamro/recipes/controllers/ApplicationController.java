package it.brunasti.abnamro.recipes.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.brunasti.abnamro.recipes.Utils;
import it.brunasti.abnamro.recipes.requests.RecipeRequest;
import it.brunasti.abnamro.recipes.responses.RecipeResponse;
import it.brunasti.abnamro.recipes.responses.RecipesListResponse;
import it.brunasti.abnamro.recipes.services.RecipesService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController("recipesApplicationController")
@RequestMapping(value = "", name="RecipesApplication RESTful APIs")
public class ApplicationController {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	private final RecipesService recipeService;

	public ApplicationController(RecipesService recipeService) {
		logger.info("ApplicationController created");
		this.recipeService = recipeService;
	}

	@Operation(summary = "Retrieve a single recipe belonging to the user, based on its unique number id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resource of the recipe",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = RecipeResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "Not authorized, the user has not logged in.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Recipe not found",
					content = @Content)
	})
	@GetMapping("/recipes/{id}")
	EntityModel<RecipeResponse> retrieveOneRecipe(
			HttpServletRequest request,
			@PathVariable Long id
	) {
		return recipeService.retrieveRecipe(id, Utils.extractToken(request));
	}

	@Operation(summary = "Retrieve multiple recipes",  description =  "Retrieve user recipes filtering " +
			"on one or more of the following criteria:\n" +
			"1. Whether or not the dish is vegetarian \n" +
			"2. The number of servings\n" +
			"3. Specific ingredients (either include or exclude)\n" +
			"4. Text search within the instructions<br>")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "List of found recipes",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = RecipesListResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "Not authorized, the user has not logged in.",
					content = @Content)
	})
	@GetMapping("/recipes")
	EntityModel<RecipesListResponse> retrieveRecipes(
			HttpServletRequest request,
			@RequestParam(required = false) Boolean vegetarian,
			@RequestParam(required = false) String instructions,
			@RequestParam(required = false) String[] includeIngredients,
			@RequestParam(required = false) String[] excludeIngredients,
			@RequestParam(required = false) Integer servings
	) {
		logger.info("retrieveRecipes");
		logger.info("retrieveRecipes vegetarian : ["+vegetarian+"]");
		logger.info("retrieveRecipes instructions : ["+instructions+"]");
		logger.info("retrieveRecipes includeIngredients : ["+Utils.arrayDump(includeIngredients)+"]");
		logger.info("retrieveRecipes excludeIngredients : ["+Utils.arrayDump(excludeIngredients)+"]");
		logger.info("retrieveRecipes servings : ["+servings+"]");
		return recipeService.retrieveRecipes(Utils.extractToken(request),
				vegetarian, instructions, includeIngredients, excludeIngredients, servings);
	}

	@Operation(summary = "Delete a recipe belonging to the user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resource of the deleted recipe",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = RecipeResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "Not authorized, the user has not logged in.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Recipe not found",
					content = @Content)
	})
	@DeleteMapping("/recipes/{id}")
	EntityModel<RecipeResponse> deleteRecipe(
			HttpServletRequest request,
			@PathVariable Long id
	) {
		logger.info("deleteRecipe : " + id);
		return recipeService.deleteRecipe(id, Utils.extractToken(request));
	}

	@Operation(summary = "Create a new recipe belonging to the user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resource of the created recipe",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = RecipeResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "Not authorized, the user has not logged in.",
					content = @Content)
	})
	@PostMapping("/recipes")
	EntityModel<RecipeResponse> createRecipe(
			HttpServletRequest request,
			@RequestBody RecipeRequest newRecipe
	) {
		logger.info("createRecipe");
		return recipeService.createRecipe(newRecipe, Utils.extractToken(request));
	}

	@Operation(summary = "Update a recipe belonging to the user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Resource of the updated recipe",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = RecipeResponse.class)) }),
			@ApiResponse(responseCode = "401", description = "Not authorized, the user has not logged in.",
					content = @Content),
			@ApiResponse(responseCode = "404", description = "Recipe not found",
					content = @Content)
	})
	@PutMapping("/recipes/{id}")
	EntityModel<RecipeResponse> updateRecipe(
			HttpServletRequest request,
			@PathVariable Long id,
			@RequestBody RecipeRequest newRecipe
	) {
		logger.info("updateRecipe : " + id);
		return recipeService.updateRecipe(id, newRecipe, Utils.extractToken(request));
	}


	@Operation(summary = "Supporto Endpoint to provide the developer with a JWT",
			description = "Used in the home page of the application at the URL: http://localhost:8080/\n" +
					"1. execute the login with the parameters:\n" +
					"  - username: paolo\n" +
					"  - password: brunasti\n" +
					"2. Press the \"Get Access Token\" button")
	@GetMapping({ "/protected/jwt" })
	public String protectedEndpoint(HttpServletRequest request) {
		logger.info("protectedEndpoint Start");

		String token = Utils.extractToken(request);
		logger.info("protectedEndpoint token : ["+token+"]");

		return "Access Token :<br><b>[ "+token+" ]</b>";
	}

}
