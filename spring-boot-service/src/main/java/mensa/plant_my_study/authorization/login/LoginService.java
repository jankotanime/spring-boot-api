package mensa.plant_my_study.authorization.login;

import mensa.plant_my_study.security.JwtConfig;
import mensa.plant_my_study.security.PasswordConfig;
import mensa.plant_my_study.user.User;
import mensa.plant_my_study.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginService {
  private final UserRepository userRepository;
  private final PasswordConfig passwordConfig;
  private final JwtConfig jwtConfig;

  public Map<String, String> tryToLogin(String username, String password) {
    Map<String, String> repsonse = new HashMap<String, String>();
    Optional<User> userOptional = userRepository.findByUsername(username);
    
    if (userOptional.isEmpty()) {
      repsonse.put("err", "Wrong username");
      return repsonse;
    }

    User user = userOptional.get();

    if (passwordConfig.verifyPassword(password, user.getPassword())) {
      String token = jwtConfig.createToken(user.getId(), user.getUsername());

      Map<String, String> response = new HashMap<>();
      response.put("token", token);
      return response;
    }

    repsonse.put("err", "Wrong password");
    return repsonse;
  }
}
