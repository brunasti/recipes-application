package it.brunasti.abnamro.recipes.db;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
public class Ingredient {

    private @Id @GeneratedValue Long id;
    private String name;
    private String description;
    private String unit;
    private Boolean vegetarian;

    protected Ingredient() {}

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Ingredient))
            return false;
        Ingredient ingredient = (Ingredient) o;
        return Objects.equals(this.id, ingredient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
