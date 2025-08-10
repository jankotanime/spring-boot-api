package mensa.plant_my_study.security.authorization.password;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.config.JwtConfig;

@RequiredArgsConstructor
@RestController
@RequestMapping("/password")
public class PasswordController {
  private final JwtConfig jwtConfig;
  private final UserRepository userRepository;
  private final PasswordService passwordService;

  @PostMapping("/set")
  public ResponseEntity<Map<String, String>> SetPassword(@RequestBody Map<String, String> reqData) {
    String password = reqData.get("password");

    Optional<User> userOptional = userRepository.findByUsername(jwtConfig.getUsernameFromJWT());

    if (userOptional.isEmpty()) {
      return ResponseEntity.status(404).body(Map.of("err", "User does not exist"));
    }

    User user = userOptional.get();

    Map<String, String> response = passwordService.SetPassword(user, password);

    if (response.containsKey("err")) {
      return ResponseEntity.status(400).body(response);
    }
    return ResponseEntity.ok(response);
  }
}
