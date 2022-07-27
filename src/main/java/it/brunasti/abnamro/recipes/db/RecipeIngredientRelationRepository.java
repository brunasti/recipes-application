package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeIngredientRelationRepository extends JpaRepository<RecipeIngredientRelation, Long> {

}
