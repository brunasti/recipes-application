package it.brunasti.abnamro.recipes.responses;

import it.brunasti.abnamro.recipes.db.Ingredient;
import it.brunasti.abnamro.recipes.db.RecipeIngredientRelation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
public class IngredientResponse {
    private long id;
    private String name;
    private String description;
    private String unit;
    private Boolean vegetarian;
    private BigDecimal quantity;

    public static IngredientResponse createFromIngredient(Ingredient ingredient, RecipeIngredientRelation recipeIngredientRelation) {
        return IngredientResponse.builder()
                .id(ingredient.getId())
                .name(ingredient.getName())
                .description(ingredient.getDescription())
                .unit(ingredient.getUnit())
                .vegetarian(ingredient.getVegetarian())
                .quantity(recipeIngredientRelation.getQuantity())
                .build();
    }
}
