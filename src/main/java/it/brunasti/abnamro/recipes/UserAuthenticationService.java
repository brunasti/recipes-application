package it.brunasti.abnamro.recipes;

import java.util.Optional;

public interface UserAuthenticationService {
  Optional<String> login(String username, String password);
  Optional<User> findByToken(String token);
  void logout(User user);
}