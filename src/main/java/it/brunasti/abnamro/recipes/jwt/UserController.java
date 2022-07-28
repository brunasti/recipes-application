package it.brunasti.abnamro.recipes.jwt;

import it.brunasti.abnamro.recipes.db.ApplicationUser;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/public")
final class UserController {
  @Autowired
  UserAuthenticationService userAuthenticationService;
  UserService userService;

//  @GetMapping("/foo")
//  String foo(){
//    return "Foobar";
//  }

  @PostMapping("/register")
  String register(
    @RequestParam("username") final String username,
    @RequestParam("password") final String password) {
    userService.save(ApplicationUser.builder().id(username).username(username).password(password).build());

      return userAuthenticationService.login(username, password)
        .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
  }

  @PostMapping("/login")
  String login(
    @RequestBody Map<String, String> body) {
      System.out.println("/login: " + body);
      return userAuthenticationService
        .login(body.get("username"), body.get("password"))
        .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
  }
}
