package it.brunasti.abnamro.recipes.db;

import org.springframework.data.jpa.repository.JpaRepository;

interface ApplicationUserReporitory extends JpaRepository<ApplicationUser, Long> {

}
