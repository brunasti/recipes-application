package it.brunasti.abnamro.recipes.responses;

import it.brunasti.abnamro.recipes.db.Ingredient;
import it.brunasti.abnamro.recipes.db.Recipe;
import it.brunasti.abnamro.recipes.db.RecipeIngredientRelation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Setter
@Getter
@Builder
public class RecipeResponse {
    private long id;
    private String ownerId;
    private String name;
    private String instructions;
    private int servings;
    private Boolean vegetarian;

    private List<IngredientResponse> ingredients;

    public static RecipeResponse createFromRecipe(Recipe recipe, Map<RecipeIngredientRelation, Ingredient> relation) {
        RecipeResponse response = RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .instructions(recipe.getInstructions())
                .ownerId(recipe.getOwnerId())
                .servings(recipe.getServings())
                .ingredients(new ArrayList<>())
                .build();

        relation.keySet().forEach(recipeIngredientRelation -> {
            Ingredient ingredient = relation.get(recipeIngredientRelation);
            response.ingredients.add(IngredientResponse.createFromIngredient(ingredient,recipeIngredientRelation));
        });

        AtomicBoolean vegetarian = new AtomicBoolean( true );
        response.ingredients.forEach(ingredientResponse -> {
            if (!ingredientResponse.getVegetarian()) {
                vegetarian.set(false);
            }
        });
        response.setVegetarian(vegetarian.get());

        return response;
    }
}
