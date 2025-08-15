package mensa.plant_my_study.main.manageUser;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
}
