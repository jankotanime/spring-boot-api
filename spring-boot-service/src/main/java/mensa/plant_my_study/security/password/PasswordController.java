package mensa.plant_my_study.security.password;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.config.JwtConfig;
import mensa.plant_my_study.security.resetToken.ResetTokenManager;

@RequiredArgsConstructor
@RestController
@RequestMapping("/password")
public class PasswordController {
  private final JwtConfig jwtConfig;
  private final UserRepository userRepository;
  private final PasswordService passwordService;
  private final ResetTokenManager resetTokenManager;

  @PostMapping("/set/mobile-app")
  public ResponseEntity<Map<String, String>> SetPasswordApp(@RequestBody Map<String, String> reqData) {
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

  @PostMapping("/set/mail")
  public ResponseEntity<Map<String, String>> SetPasswordMail(@RequestBody Map<String, String> reqData) {
    String password = reqData.get("password");
    String tokenIdString = reqData.get("token-id");
    String token = reqData.get("token");

    UUID tokenId;

    try {
      tokenId = UUID.fromString(tokenIdString);
    } catch (IllegalArgumentException | NullPointerException e) {
      return ResponseEntity.status(422).body(Map.of("err", "Bad refresh token id format"));
    }

    User user = resetTokenManager.validateResetTokenAndGetUser(tokenId, token);

    if (user == null) {
      return ResponseEntity.status(400).body(Map.of("err", "Unathorized"));
    }

    Map<String, String> response = passwordService.SetPassword(user, password);

    if (response.containsKey("err")) {
      return ResponseEntity.status(400).body(response);
    }
    return ResponseEntity.ok(response);
  }
}
