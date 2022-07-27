package it.brunasti.abnamro.recipes.db;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestEntityManager
public class JPAApplicationUserUnitTest {


    @Autowired
    ApplicationUserReporitory applicationUserReporitory;

    @Test
    public void save_user() {
        ApplicationUser applicationUser = applicationUserReporitory.save(
                ApplicationUser.builder().id("paolo").username("Paolo").build()
        );
        assertThat(applicationUser).hasFieldOrPropertyWithValue("username", "Paolo");
    }

    @Test
    public void should_find_user() {
        String paoloId;
        ApplicationUser applicationUser = applicationUserReporitory.save(
                ApplicationUser.builder().id("paolo").username("Paolo").build()
        );
        paoloId = applicationUser.getId();

        applicationUserReporitory.save(
                ApplicationUser.builder().id("mario").username("Mario").build()
        );

        Optional<ApplicationUser> findApplicationUser = applicationUserReporitory.findById(paoloId);
        assert(findApplicationUser.isPresent());
        assertThat(findApplicationUser.get()).hasFieldOrPropertyWithValue("username", "Paolo");
    }

    @Test
    public void should_fail_create_double_user() {
        String paoloId = "paolo";
        ApplicationUser applicationUser = applicationUserReporitory.save(
                ApplicationUser.builder().id(paoloId).username("Paolo").build()
        );

        Optional<ApplicationUser> findApplicationUser = applicationUserReporitory.findById(paoloId);
        assert(findApplicationUser.isPresent());
        assertThat(findApplicationUser.get()).hasFieldOrPropertyWithValue("id", paoloId);
        assertThat(findApplicationUser.get()).hasFieldOrPropertyWithValue("username", "Paolo");

        applicationUserReporitory.save(
                ApplicationUser.builder().id("paolo").username("Mario").build()
        );

        findApplicationUser = applicationUserReporitory.findById(paoloId);
        assert(findApplicationUser.isPresent());
        assertThat(findApplicationUser.get()).hasFieldOrPropertyWithValue("id", paoloId);
        assertThat(findApplicationUser.get()).hasFieldOrPropertyWithValue("username", "Mario");

        findApplicationUser = applicationUserReporitory.findByUsername("Mario");
        assert(findApplicationUser.isPresent());
        assertThat(findApplicationUser.get()).hasFieldOrPropertyWithValue("id", paoloId);
        assertThat(findApplicationUser.get()).hasFieldOrPropertyWithValue("username", "Mario");
    }

    @Test
    public void compare_user() {
        ApplicationUser paolo = applicationUserReporitory.save(
                ApplicationUser.builder().id("paolo").username("Paolo").build()
        );

        ApplicationUser mario = applicationUserReporitory.save(
                ApplicationUser.builder().id("mario").username("Mario").build()
        );

        assert(!paolo.equals(mario));
        assert(paolo.hashCode() != mario.hashCode());
    }

    @Test
    public void should_find_nothing_is_repository_empty() {
        Iterable<ApplicationUser> applicationUserReporitoryAll = applicationUserReporitory.findAll();
        assert(!applicationUserReporitoryAll.iterator().hasNext());
    }
}
