package it.brunasti.abnamro.recipes;

//import it.brunasti.abnamro.recipes.jwt.JwtApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecipesApplication {
	public static void main(String[] args) {

		SpringApplication.run(RecipesApplication.class, args);
//		SpringApplication.run(JwtApplication.class, args);

	}
}
