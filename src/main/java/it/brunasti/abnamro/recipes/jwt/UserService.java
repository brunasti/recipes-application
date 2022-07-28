package it.brunasti.abnamro.recipes.jwt;

import it.brunasti.abnamro.recipes.db.ApplicationUser;

import java.util.Optional;

public interface UserService {

  ApplicationUser  save(ApplicationUser  user);

  Optional<ApplicationUser > find(String id);

  Optional<ApplicationUser > findByUsername(String username);
}