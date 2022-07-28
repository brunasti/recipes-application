package it.brunasti.abnamro.recipes.jwt;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public final class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

  private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

  @Autowired
  UserAuthenticationService auth;

  @Override
  protected void additionalAuthenticationChecks(final UserDetails d, final UsernamePasswordAuthenticationToken auth) {
  }

  @Override
  protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {
    logger.info("retrieveUser ["+username+"]");
    final Object token = authentication.getCredentials();
    logger.info("retrieveUser token ["+token+"]");
    return Optional.ofNullable(token).map(String::valueOf).flatMap(auth::findByToken)
            .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user: " + token));
  }
}