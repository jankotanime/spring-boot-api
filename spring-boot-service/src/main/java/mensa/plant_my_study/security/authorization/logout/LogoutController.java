package mensa.plant_my_study.security.authorization.logout;

import java.util.Map;
import java.util.UUID;

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
@RequestMapping("/logout")
public class LogoutController {
  private final LogoutService logoutService;

  @PostMapping
  public ResponseEntity<Map<String, String>> TryToLogout(@RequestBody Map<String, String> reqData) {
    if (!reqData.containsKey("refresh-token") || !reqData.containsKey("refresh-token-id")) {
      return ResponseEntity.status(400).body(Map.of("err", "Bad request"));
    }

    String tokenIdString = reqData.get("refresh-token-id");
    String token = reqData.get("refresh-token");

    UUID tokenId;

    try {
      tokenId = UUID.fromString(tokenIdString);
    } catch (IllegalArgumentException | NullPointerException e) {
      return ResponseEntity.status(422).body(Map.of("err", "Bad refresh token id format"));
    }

    Map<String, String> response = logoutService.tryToLogout(tokenId, token);

    if (response.containsKey("err")) {
      return ResponseEntity.status(401).body(response);
    }

    return ResponseEntity.ok(response);
  }

  @PostMapping("/all")
  public ResponseEntity<Map<String, String>> tryToLogoutFromAllDevices(@RequestBody Map<String, String> reqData) {
    if (!reqData.containsKey("refresh-token") || !reqData.containsKey("refresh-token-id")) {
      return ResponseEntity.status(400).body(Map.of("err", "Bad request"));
    }

    String tokenIdString = reqData.get("refresh-token-id");
    String token = reqData.get("refresh-token");

    UUID tokenId;

    try {
      tokenId = UUID.fromString(tokenIdString);
    } catch (IllegalArgumentException | NullPointerException e) {
      return ResponseEntity.status(422).body(Map.of("err", "Bad refresh token id format"));
    }

    Map<String, String> response = logoutService.tryToLogoutFromAllDevices(tokenId, token);

    if (response.containsKey("err")) {
      return ResponseEntity.status(401).body(response);
    }

    return ResponseEntity.ok(response);
  }
}
