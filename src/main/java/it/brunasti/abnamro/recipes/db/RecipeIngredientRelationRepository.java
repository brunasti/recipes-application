package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

interface RecipeIngredientRelationRepository extends JpaRepository<RecipeIngredientRelation, Long> {

}
