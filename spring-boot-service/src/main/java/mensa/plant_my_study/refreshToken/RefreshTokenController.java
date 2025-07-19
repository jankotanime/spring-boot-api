package mensa.plant_my_study.refreshToken;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/refresh-token")
public class RefreshTokenController {
  private final RefreshTokenService refreshTokenService;

  @PostMapping
  public ResponseEntity<Map<String, String>> TryToRefreshTokenController(@RequestBody Map<String, String> reqData) {
    System.out.println("Poczatek kontroller");
    if (!reqData.containsKey("refresh-token")) {
      return ResponseEntity.status(403).body(Map.of("err", "Bad request"));
    }
    
    String token = reqData.get("refresh-token");
    
    Map<String, String> response = refreshTokenService.refreshRefreshToken(token);
    
    System.out.println(response);
    if (response.containsKey("err")) {
      return ResponseEntity.status(403).body(response);
    }

    return ResponseEntity.status(200).body(response);
  }
}
