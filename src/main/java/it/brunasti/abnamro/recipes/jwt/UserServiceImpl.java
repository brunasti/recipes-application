package it.brunasti.abnamro.recipes.jwt;

import it.brunasti.abnamro.recipes.db.ApplicationUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public final class UserServiceImpl implements UserService {

  Map<String, ApplicationUser > users = new HashMap<>();

  @Override
  public ApplicationUser save(final ApplicationUser user) {
    return users.put(user.getId(), user);
  }

  @Override
  public Optional<ApplicationUser > find(final String id) {
    return ofNullable(users.get(id));
  }

  @Override
  public Optional<ApplicationUser > findByUsername(final String username) {
    return users
      .values()
      .stream()
      .filter(u -> Objects.equals(username, u.getUsername()))
      .findFirst();
  }
}

