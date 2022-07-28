package it.brunasti.abnamro.recipes.jwt;

import it.brunasti.abnamro.recipes.db.ApplicationUser;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
final class UserServiceImpl implements UserService {

  // TODO - PB
  // rewrite to match the DAO classes and tables

  Map<String, ApplicationUser > users = new HashMap<>()
  {{
    put("Paolo", new ApplicationUser ("paolo","paolo","brunasti"));
    put("Mario", new ApplicationUser ("mario","mario","luigi"));
  }};

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

