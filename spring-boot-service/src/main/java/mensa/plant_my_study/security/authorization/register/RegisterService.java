package mensa.plant_my_study.security.authorization.register;

import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.accessToken.AccessTokenManager;
import mensa.plant_my_study.security.config.PasswordConfig;
import mensa.plant_my_study.security.refreshToken.RefreshTokenManager;
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
  private final RefreshTokenManager refreshTokenManager;
  private final AccessTokenManager accessTokenManager;

  public static boolean patternMatches(String emailAddress, String regexPattern) {
    return Pattern.compile(regexPattern)
      .matcher(emailAddress)
      .matches();
  }

  public Map<String, String> tryToRegister(String username, String email, String password) {
    Map<String, String> response = new HashMap<String, String>();
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
      response.put("err", "Username taken");
      return response;
    }

    userOptional = userRepository.findByEmail(email);
    if (userOptional.isPresent()) {
      response.put("err", "Email taken");
      return response;
    }

    if (!patternMatches(email, "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-zA-Z0-9.-]+$")) {
      response.put("err", "Wrong email");
      return response;
    }
    if (!patternMatches(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$")) {
      response.put("err", "Wrong password");
      return response;
    }

    String hashedPassword = passwordConfig.passwordEncoder().encode(password);
    User user = new User(username, email, hashedPassword, null);
    userRepository.save(user);
    Map<String, String> newRefreshToken = refreshTokenManager.generateRefreshToken(user.getId());
    String accessToken = accessTokenManager.GenerateToken(user);
    response.putAll(newRefreshToken);
    response.put("access-token", accessToken);
    return response;
  }
}
