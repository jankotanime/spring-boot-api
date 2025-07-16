package mensa.plant_my_study.authorization.login;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/login")
public class LoginController {
  private final LoginService loginService;

  @PostMapping
  public ResponseEntity<Map<String, String>> getAllUsers(@RequestBody Map<String, String> reqData) {

    if (!reqData.containsKey("username") || !reqData.containsKey("password")) {
      return ResponseEntity.status(403).body(Map.of("err", "Bad request"));
    }

    String username = reqData.get("username");
    String password = reqData.get("password");

    Map<String, String> response = loginService.tryToLogin(username, password);

    if (response.containsKey("err")) {
      return ResponseEntity.status(403).body(response);
    } else {
      return ResponseEntity.ok(response); 
    }
  }
}
