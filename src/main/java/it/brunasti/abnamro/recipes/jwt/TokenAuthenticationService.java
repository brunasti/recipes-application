package it.brunasti.abnamro.recipes.jwt;

import com.google.common.collect.ImmutableMap;
import it.brunasti.abnamro.recipes.db.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.inject.Singleton;
import java.util.Objects;
import java.util.Optional;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Component
@Singleton
@Slf4j
//@Service
@AllArgsConstructor
//@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class TokenAuthenticationService implements UserAuthenticationService {

  private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

  @Autowired
  TokenService tokenService;
  @Autowired
  UserService users;

  @Override
  public Optional<String> login(final String username, final String password) {
    logger.info("login ["+username+"]");
    return users
      .findByUsername(username)
      .filter(user -> Objects.equals(password, user.getPassword()))
      .map(user -> tokenService.newToken(ImmutableMap.of("username", username)));
  }

  @Override
  public Optional<ApplicationUser> findByToken(final String token) {
    logger.info("findByToken ["+token+"]");
    return Optional
      .of(tokenService.verify(token))
      .map(map -> map.get("username"))
      .flatMap(users::findByUsername);
  }

  @Override
  public void logout(final ApplicationUser user) {
  }
}