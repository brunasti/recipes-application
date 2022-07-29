package it.brunasti.abnamro.recipes.requests;

import it.brunasti.abnamro.recipes.db.Ingredient;
import org.junit.jupiter.api.Test;

public class IngredientRequestTest {

    @Test
    public void toIngredient_Test() {
        IngredientRequest ingredientRequest = IngredientRequest.builder()
                .description("Description")
                .name("Name")
                .unit("Unit")
                .build();

        Ingredient ingredient = ingredientRequest.toIngredient();
        assert(ingredient!=null);

        assert(ingredient.getName().equals("Name"));
        assert(ingredient.getUnit().equals("Unit"));
        assert(ingredient.getDescription().equals("Description"));
    }

}
