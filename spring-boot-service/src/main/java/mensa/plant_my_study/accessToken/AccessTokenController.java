package mensa.plant_my_study.accessToken;

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
@RequestMapping("/access-token")
public class AccessTokenController {
  private final AccessTokenService accessTokenService;

  @PostMapping
  public ResponseEntity<Map<String, String>> GetTokenController(@RequestBody Map<String, String> reqData) {
    if (!reqData.containsKey("refresh-token")) {
      return ResponseEntity.status(403).body(Map.of("err", "Bad request"));
    }

    String refreshToken = reqData.get("refresh-token");

    Map<String, String> response = accessTokenService.GetToken(refreshToken);

    if (response.containsKey("err")) {
      return ResponseEntity.status(403).body(response);
    }

    return ResponseEntity.status(200).body(response);
  }
}
