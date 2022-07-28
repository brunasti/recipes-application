package it.brunasti.abnamro.recipes.requests;

import it.brunasti.abnamro.recipes.db.Ingredient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@ToString
public class IngredientRequest {
    private String name;
    private String description;
    private String unit;
    private Boolean vegetarian;
    private BigDecimal quantity;

    public Ingredient toIngredent() {
        return Ingredient.builder().name(name).unit(unit).description(description).vegetarian(vegetarian).build();
    }
}
