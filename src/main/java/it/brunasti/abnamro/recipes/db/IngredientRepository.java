package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
