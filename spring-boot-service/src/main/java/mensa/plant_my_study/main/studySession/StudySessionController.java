package mensa.plant_my_study.main.studySession;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.config.JwtConfig;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/study-session")
public class StudySessionController {
  private final StudySessionService studySessionService;
  private final UserRepository userRepository;
  private final JwtConfig jwtConfig;

  @PostMapping
  public ResponseEntity<Map<String, String>> postStudySession(@RequestBody Map<String, String> reqData) {
    if (!reqData.containsKey("study-time")) {
      return ResponseEntity.status(400).body(Map.of("err", "Bad request"));
    }

    Integer studyTime = Integer.parseInt(reqData.get("study-time"));
    Instant sessionEndAt = Instant.now();

    if (reqData.containsKey("session-end-at")) {
      sessionEndAt = Instant.parse(reqData.get("session-end-at"));
    }
    Optional<User> userOptional = userRepository.findByUsername(jwtConfig.getUsernameFromJWT());

    if (userOptional.isEmpty()) {
      return ResponseEntity.status(404).body(Map.of("err", "User does not exist"));
    }

    User user = userOptional.get();

    Map<String, String> response = studySessionService.postSession(user, studyTime, sessionEndAt);

    if (response.containsKey("err")) {
      return ResponseEntity.status(404).body(response);
    }
    return ResponseEntity.ok(response);
  }
}
