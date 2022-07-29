package it.brunasti.abnamro.recipes.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public")
final class UserController {
  @Autowired
  UserAuthenticationService userAuthenticationService;

  @PostMapping("/login")
  String login(
    @RequestBody Map<String, String> body) {
      System.out.println("/login: " + body);
      return userAuthenticationService
        .login(body.get("username"), body.get("password"))
        .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
  }
}
