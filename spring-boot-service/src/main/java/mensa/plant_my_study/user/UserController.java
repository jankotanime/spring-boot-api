package mensa.plant_my_study.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  @GetMapping("/")
  public ResponseEntity<List<User>> getAllUsers(){
    return ResponseEntity.ok(userService.getAllUsers());
  }
}
