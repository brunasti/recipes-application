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
public class JPAIngredientUnitTest {


    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    public void save_ingredient() {
        Ingredient ingredient = ingredientRepository.save(
                Ingredient.builder().name("acqua").vegetarian(true).description("H2O").unit("g").build()
        );
        assertThat(ingredient).hasFieldOrPropertyWithValue("name", "acqua");
        assertThat(ingredient).hasFieldOrPropertyWithValue("description", "H2O");
        assertThat(ingredient).hasFieldOrPropertyWithValue("unit", "g");
        assertThat(ingredient).hasFieldOrPropertyWithValue("vegetarian", true);
    }

    @Test
    public void should_find_nothing_is_repository_empty() {
        Iterable<Ingredient> ingredientRepositoryAll = ingredientRepository.findAll();
        assert(!ingredientRepositoryAll.iterator().hasNext());
    }

    @Test
    public void should_find_ingredient() {
        Long acquaId;
        Ingredient ingredient = ingredientRepository.save(
                Ingredient.builder().name("acqua").vegetarian(true).description("H2O").unit("g").build()
        );

        acquaId = ingredient.getId();

        Optional<Ingredient> find = ingredientRepository.findById(acquaId);
        assert(find.isPresent());

        ingredient = find.get();
        assertThat(ingredient).hasFieldOrPropertyWithValue("name", "acqua");
        assertThat(ingredient).hasFieldOrPropertyWithValue("description", "H2O");
        assertThat(ingredient).hasFieldOrPropertyWithValue("unit", "g");
        assertThat(ingredient).hasFieldOrPropertyWithValue("vegetarian", true);
    }

    @Test
    public void should_find_ingredient_by_name() {
        String acquaName = "acqua";
        ingredientRepository.save(
                Ingredient.builder().name(acquaName).vegetarian(true).description("H2O").unit("g").build()
        );

        Optional<Ingredient> find = ingredientRepository.findByName(acquaName);
        assert(find.isPresent());

        Ingredient ingredient = find.get();
        assertThat(ingredient).hasFieldOrPropertyWithValue("name", acquaName);
        assertThat(ingredient).hasFieldOrPropertyWithValue("description", "H2O");
        assertThat(ingredient).hasFieldOrPropertyWithValue("unit", "g");
        assertThat(ingredient).hasFieldOrPropertyWithValue("vegetarian", true);
    }


    @Test
    public void compare_ingredient() {
        Ingredient acqua = ingredientRepository.save(
                Ingredient.builder().name("acqua").vegetarian(true).description("H2O").unit("g").build()
        );

        Ingredient burro = ingredientRepository.save(
                Ingredient.builder().name("burro").vegetarian(false).description("Burro di latte vaccino").unit("g").build()
        );

        assert(!acqua.equals(burro));
        assert(acqua.hashCode() != burro.hashCode());
        assert(acqua.hashCode() == acqua.hashCode());
    }

}
