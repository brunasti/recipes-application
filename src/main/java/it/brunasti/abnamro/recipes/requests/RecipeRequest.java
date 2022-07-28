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
public class RecipeRequest {
//    private String ownerId;
    private String name;
    private String instructions;
    private int servings;

    private List<IngredientRequest> ingredients;

    public Recipe toRecipe(String ownerId) {
        return Recipe.builder().instructions(instructions).servings(servings).name(name).ownerId(ownerId).build();
    }
}
