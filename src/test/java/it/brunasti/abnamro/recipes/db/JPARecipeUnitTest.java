package it.brunasti.abnamro.recipes.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestEntityManager
public class JPARecipeUnitTest {


    @Autowired
    RecipeRepository recipeRepository;

    @Test
    public void save_recipe() {
        Recipe recipe = recipeRepository.save(
                Recipe.builder().name("spaghetti al burro").instructions("Bollire l'acqua").build()
        );
        assertThat(recipe).hasFieldOrPropertyWithValue("name", "spaghetti al burro");
        assertThat(recipe).hasFieldOrPropertyWithValue("instructions", "Bollire l'acqua");
    }

    @Test
    public void should_find_nothing_is_repository_empty() {
        Iterable<Recipe> recipeRepositoryAll = recipeRepository.findAll();
        assert(!recipeRepositoryAll.iterator().hasNext());
    }

    @Test
    public void should_find_recipe() {
        Long spaghettiBurroId;
        Recipe recipe = recipeRepository.save(
                Recipe.builder().name("spaghetti al burro").instructions("Bollire l'acqua").build()
        );

        spaghettiBurroId = recipe.getId();

        Optional<Recipe> find = recipeRepository.findById(spaghettiBurroId);
        assert(find.isPresent());

        recipe = find.get();
        assertThat(recipe).hasFieldOrPropertyWithValue("name", "spaghetti al burro");
        assertThat(recipe).hasFieldOrPropertyWithValue("instructions", "Bollire l'acqua");
    }

    @Test
    public void should_find_recipe_by_name() {
        String spaghettiBurroName = "spaghetti al burro";
        recipeRepository.save(
                Recipe.builder().name(spaghettiBurroName).instructions("Bollire l'acqua").build()
        );

        Optional<Recipe> find = recipeRepository.findByName(spaghettiBurroName);
        assert(find.isPresent());

        Recipe recipe = find.get();
        assertThat(recipe).hasFieldOrPropertyWithValue("name", spaghettiBurroName);
        assertThat(recipe).hasFieldOrPropertyWithValue("instructions", "Bollire l'acqua");
    }


    @Test
    public void compare_recipe() {
        Recipe spaghettiBurro = recipeRepository.save(
                Recipe.builder().name("spaghetti al burro").instructions("Bollire l'acqua").build()
        );

        Recipe burro = recipeRepository.save(
                Recipe.builder().name("burro").instructions("Burro di latte vaccino").build()
        );

        assert(!spaghettiBurro.equals(burro));
        assert(spaghettiBurro.hashCode() != burro.hashCode());
        assert(spaghettiBurro.hashCode() == spaghettiBurro.hashCode());
    }

}
