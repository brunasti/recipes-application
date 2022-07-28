package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRelationRepository extends JpaRepository<RecipeIngredientRelation, Long> {
    List<RecipeIngredientRelation> findByRecipeId(Long recipeId);
    Long deleteByRecipeId(Long recipeId);
}
