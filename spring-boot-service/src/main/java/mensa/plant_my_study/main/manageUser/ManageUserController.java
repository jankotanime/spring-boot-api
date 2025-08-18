package mensa.plant_my_study.main.manageUser;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.security.config.JwtConfig;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/manage-user")
public class ManageUserController {
  private final JwtConfig jwtConfig;
  private final ManageUserService manageUserService;

  @DeleteMapping("/delete")
  public ResponseEntity<Map<String, String>> StartDeletingUser() {
    String username = jwtConfig.getUsernameFromJWT();

    Map<String, String> response = manageUserService.StartDeletingUser(username);

    if (response.containsKey("err")) {
      return ResponseEntity.status(404).body(response);
    }
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/change-email")
  public ResponseEntity<Map<String, String>> changeEmail(@RequestBody Map<String, String> reqData) {
    String username = jwtConfig.getUsernameFromJWT();

    if (!reqData.containsKey("new-email")) {
      return ResponseEntity.status(400).body(Map.of("err", "Bad request"));
    }

    String email = reqData.get("new-email");

    Map<String, String> response = manageUserService.changeEmail(username, email);

    if (response.containsKey("err")) {
      return ResponseEntity.status(404).body(response);
    }
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/change-username")
  public ResponseEntity<Map<String, String>> changeUsername(@RequestBody Map<String, String> reqData) {
    String username = jwtConfig.getUsernameFromJWT();

    if (!reqData.containsKey("new-username")) {
      return ResponseEntity.status(400).body(Map.of("err", "Bad request"));
    }

    String newUsername = reqData.get("new-username");

    Map<String, String> response = manageUserService.changeUsername(username, newUsername);

    if (response.containsKey("err")) {
      return ResponseEntity.status(404).body(response);
    }
    return ResponseEntity.ok(response);
  }
}
