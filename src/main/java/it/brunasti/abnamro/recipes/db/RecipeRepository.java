package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByOwnerId(String ownerId);
    Optional<Recipe> findByName(String name);
    List<Recipe> findByInstructionsContaining(String instructions);
    Optional<Recipe> findByNameAndOwnerId(String name, String username);

}
