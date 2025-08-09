package mensa.plant_my_study.security.authorization.password;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/password")
public class PasswordController {
  private final PasswordService passwordService;

  @PostMapping("/reset")
  public ResponseEntity<Map<String, String>> SendMailResetPassword(@RequestBody Map<String, String> reqData) {
    String email = reqData.get("email");
    String language = reqData.get("lang");

    if (!language.equals("pl") && !language.equals("en")) {
      Map<String, String> response = new HashMap<>();
      response.put("err", "Wrong language");
      return ResponseEntity.status(404).body(response);
    }

    Map<String, String> response = passwordService.SendMailResetPassword(email, language);

    if (response.containsKey("err")) {
      return ResponseEntity.status(400).body(response);
    }
    return ResponseEntity.ok(response);
  }
}
