package it.brunasti.abnamro.recipes;

import it.brunasti.abnamro.recipes.jwt.TokenAuthenticationProvider;
import it.brunasti.abnamro.recipes.jwt.TokenAuthenticationService;
import it.brunasti.abnamro.recipes.requests.RecipeRequest;
import it.brunasti.abnamro.recipes.responses.RecipeResponse;
import it.brunasti.abnamro.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.removeStart;

@Slf4j
@RestController
@RequestMapping("")
class ApplicationController {
	private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	private final RecipeService recipeService;
//	private final TokenAuthenticationService tokenAuthenticationService;
//	private final TokenAuthenticationProvider tokenAuthenticationProvider;

	public ApplicationController(RecipeService recipeService, HttpServletRequest request) {
		logger.info("ApplicationController created");
		this.recipeService = recipeService;
	}

	@GetMapping("/recipes/{id}")
	EntityModel<RecipeResponse> retieveOneRecipe(@PathVariable Long id, HttpServletRequest request) {
//		logger.info("retieveOneRecipe : " + id);
//		String token = extractToken(request);
//		logger.info("retieveOneRecipe token : ["+token+"]");
		return recipeService.retrieveRecipe(id, extractToken(request));
	}

	@DeleteMapping("/recipes/{id}")
	EntityModel<RecipeResponse> deleteRecipe(@PathVariable Long id, HttpServletRequest request) {
		logger.info("deleteRecipe : " + id);
		return recipeService.deleteRecipe(id, extractToken(request));
	}

	@PostMapping("/recipes")
	EntityModel<RecipeResponse> createRecipe(@RequestBody RecipeRequest newRecipe, HttpServletRequest request) {
		logger.info("createRecipe");
		return recipeService.createRecipe(newRecipe, extractToken(request));
	}

	@PutMapping("/recipes/{id}")
	EntityModel<RecipeResponse> updateRecipe(@PathVariable Long id, @RequestBody RecipeRequest newRecipe, HttpServletRequest request) {
		logger.info("updateRecipe : " + id);
		return recipeService.updateRecipe(id, newRecipe, extractToken(request));
	}




	@GetMapping({ "/public" })
	public String publicEndpoint() {
		return "Public Endpoint Response";
	}

	@GetMapping({ "/protected" })
	public String protectedEndpoint(HttpServletRequest request) {
		logger.info("protectedEndpoint Start");
//		final String param = ofNullable(request.getHeader(AUTHORIZATION)).orElse(request.getParameter("t"));
//
//		final String token = ofNullable(param).map(value -> removeStart(value, "Bearer"))
//				.map(String::trim).orElseThrow(() -> new BadCredentialsException("No Token Found!"));

		String token = extractToken(request);
		logger.info("protectedEndpoint token : ["+token+"]");

		return "Access Token :<br><b>[ "+token+" ]</b>";
	}



	private String extractToken(HttpServletRequest request) {
		final String param = ofNullable(request.getHeader(AUTHORIZATION)).orElse(request.getParameter("t"));
		return ofNullable(param).map(value -> removeStart(value, "Bearer"))
				.map(String::trim).orElseThrow(() -> new BadCredentialsException("No Token Found!"));
	}
}
