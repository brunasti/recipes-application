package it.brunasti.abnamro.recipes.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
@Entity
public class RecipeIngredientRelation {
    private @Id @GeneratedValue Long id;
    private Long recipeId;
    private Long ingredientId;
    private BigDecimal quantity;

    protected RecipeIngredientRelation() {}

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof RecipeIngredientRelation))
            return false;
        RecipeIngredientRelation recipeIngredientRelation = (RecipeIngredientRelation) o;
        return Objects.equals(this.id, recipeIngredientRelation.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
