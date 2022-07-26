package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

interface RecipeRepository extends JpaRepository<Recipe, Long> {

}
