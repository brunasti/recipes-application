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
public class Recipe {

    private @Id @GeneratedValue Long id;
    private Long ownerId;
    private String name;
    private String instructions;
    private int servings;

    protected Recipe() {}

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Recipe))
            return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(this.id, recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

}
