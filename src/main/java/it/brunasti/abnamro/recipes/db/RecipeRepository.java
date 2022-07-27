package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByOwnerId(String ownerId);
    List<Recipe> findByName(String name);
    List<Recipe> findByInstructionsContaining(String instructions);

}
