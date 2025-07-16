package mensa.plant_my_study.authorization.register;

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

public class RegisterService {
  private final UserRepository userRepository;

  public Map<String, String> tryToRegister(String username, String email, String password) {
    Map<String, String> repsonse = new HashMap<String, String>();
    Optional<User> isUser = userRepository.findByUsername(username);
    if (isUser.isPresent()) {
      repsonse.put("err", "Username taken");
      return repsonse;
    }

    isUser = userRepository.findByEmail(email);
    if (isUser.isPresent()) {
      repsonse.put("err", "Email taken");
      return repsonse;
    }

    User user = new User(username, email, password);
    userRepository.save(user);
    repsonse.put("ok", "ok");
    return repsonse;
  }
}
