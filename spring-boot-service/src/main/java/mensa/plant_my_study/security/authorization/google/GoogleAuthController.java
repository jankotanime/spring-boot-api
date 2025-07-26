package mensa.plant_my_study.security.authorization.google;

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
@RequestMapping("/google/auth")
public class GoogleAuthController {
  private final GoogleAuthService googleAuthService;

  @PostMapping
  public ResponseEntity<Map<String, String>> tryToLoginWithGoogleController(@RequestBody Map<String, String> reqData) {

    if (!reqData.containsKey("id-token")) {
      return ResponseEntity.status(400).body(Map.of("err", "Bad request"));
    }

    String idToken = reqData.get("id-token");

    Map<String, String> response = googleAuthService.tryToLoginWithGoogle(idToken);

    if (response.containsKey("err")) {
      return ResponseEntity.status(401).body(response);
    } else {
      return ResponseEntity.ok(response);
    }
  }
}
