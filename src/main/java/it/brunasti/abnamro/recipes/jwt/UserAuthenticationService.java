package it.brunasti.abnamro.recipes.jwt;

import it.brunasti.abnamro.recipes.db.ApplicationUser;

import java.util.Optional;

public interface UserAuthenticationService {
  Optional<String> login(String username, String password);
  Optional<ApplicationUser> findByToken(String token);
  void logout(ApplicationUser  user);
}