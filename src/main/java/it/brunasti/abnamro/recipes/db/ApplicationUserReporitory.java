package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserReporitory extends JpaRepository<ApplicationUser, String> {
    Optional<ApplicationUser> findByUsername(String username);
}
