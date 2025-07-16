package mensa.plant_my_study.authorization.register;

import mensa.plant_my_study.user.User;
import mensa.plant_my_study.user.UserRepository;
import mensa.plant_my_study.security.PasswordConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class RegisterService {
  private final UserRepository userRepository;
  private final PasswordConfig passwordConfig;

  public static boolean patternMatches(String emailAddress, String regexPattern) {
    return Pattern.compile(regexPattern)
      .matcher(emailAddress)
      .matches();
  }

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

    if (!patternMatches(email, "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-zA-Z0-9.-]+$")) {
      repsonse.put("err", "Wrong email");
      return repsonse;
    }
    if (!patternMatches(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
      repsonse.put("err", "Wrong password");
      return repsonse;
    }

    String hashedPassword = passwordConfig.passwordEncoder().encode(password);
    User user = new User(username, email, hashedPassword);
    userRepository.save(user);
    repsonse.put("ok", "ok");
    return repsonse;
  }
}
