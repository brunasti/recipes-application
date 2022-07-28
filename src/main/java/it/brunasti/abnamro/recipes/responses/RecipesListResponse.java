package it.brunasti.abnamro.recipes.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class RecipesListResponse {
    private List<RecipeResponse> recipes = new ArrayList<>();
}
