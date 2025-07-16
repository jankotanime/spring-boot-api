package mensa.plant_my_study.authorization.login;

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

  public Map<String, String> tryToLogin(String username, String password) {
    Map<String, String> repsonse = new HashMap<String, String>();
    Optional<User> user = userRepository.findByUsername(username);
    
    if (user.isEmpty()) {
      repsonse.put("err", "Wrong username");
      return repsonse;
    }

    if (user.get().getPassword().equals(password)) {
      repsonse.put("ok", "ok");
      return repsonse;
    }

    repsonse.put("err", "Wrong password");
    return repsonse;
  }
}
