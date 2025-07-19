package mensa.plant_my_study.authorization.register;

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
@RequestMapping("/register")
public class RegisterController {
  private final RegisterService registrationService;
  @PostMapping
  public ResponseEntity<Map<String, String>> tryToRegisterController(@RequestBody Map<String, String> reqData) {
    if (!reqData.containsKey("username") || !reqData.containsKey("password") 
        || !reqData.containsKey("email")) {
      return ResponseEntity.status(403).body(Map.of("err", "Bad request"));
    }

    String username = reqData.get("username");
    String email = reqData.get("email");
    String password = reqData.get("password");

    System.out.println("przed service");

    Map<String, String> repsonse = registrationService.tryToRegister(username, email, password);
    
    if (repsonse.containsKey("err")) {
      return ResponseEntity.status(403).body(repsonse);
    } else {
      return ResponseEntity.ok(repsonse); 
    }
  }
}
