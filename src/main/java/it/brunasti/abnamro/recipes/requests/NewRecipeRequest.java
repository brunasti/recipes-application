package it.brunasti.abnamro.recipes.requests;

import it.brunasti.abnamro.recipes.db.Recipe;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class NewRecipeRequest {
    private String ownerId;
    private String name;
    private String instructions;
    private int servings;

    private List<NewIngredientRequest> ingredients;

    public Recipe toRecipe() {
        return Recipe.builder().instructions(instructions).servings(servings).name(name).ownerId(ownerId).build();
    }
}
