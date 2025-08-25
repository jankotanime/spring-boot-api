package mensa.plant_my_study.security.authorization.login;

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
  public ResponseEntity<Map<String, String>> tryToLoginController(@RequestBody Map<String, String> reqData) {

    if (!reqData.containsKey("login-data") || !reqData.containsKey("password")) {
      return ResponseEntity.status(400).body(Map.of("err", "Bad request"));
    }

    String loginData = reqData.get("login-data");
    String password = reqData.get("password");

    Map<String, String> response = loginService.tryToLogin(loginData, password);

    if (response.containsKey("err")) {
      if (response.get("err").equals("Password not set")) {
        return ResponseEntity.status(409).body(response);
      }
      return ResponseEntity.status(401).body(response);
    } else {
      return ResponseEntity.ok(response);
    }
  }
}
