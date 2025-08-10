package mensa.plant_my_study.security.authorization.password;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.config.PasswordConfig;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordService {
  private final UserRepository userRepository;
  private final PasswordConfig passwordConfig;

  public static boolean patternMatches(String emailAddress, String regexPattern) {
    return Pattern.compile(regexPattern)
      .matcher(emailAddress)
      .matches();
  }

  public Map<String, String> SetPassword(final User user, final String password) {
    Map<String, String> response = new HashMap<>();

    if (!patternMatches(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
      response.put("err", "Wrong password");
      return response;
    }

    String hashedPassword = passwordConfig.passwordEncoder().encode(password);
    user.setPassword(hashedPassword);
    userRepository.save(user);

    response.put("data", "Password set");
    return response;
  }
}
