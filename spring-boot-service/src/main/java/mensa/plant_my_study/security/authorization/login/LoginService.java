package mensa.plant_my_study.security.authorization.login;

import mensa.plant_my_study.security.accessToken.AccessTokenManager;
import mensa.plant_my_study.security.config.PasswordConfig;
import mensa.plant_my_study.security.refreshToken.RefreshTokenManager;
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
  private final RefreshTokenManager refreshTokenManager;
  private final AccessTokenManager accessTokenManager;
  private final UserRepository userRepository;
  private final PasswordConfig passwordConfig;

  public Map<String, String> tryToLogin(String loginData, String password) {
    Map<String, String> response = new HashMap<String, String>();
    Optional<User> userOptional = userRepository.findByUsername(loginData);

    if (userOptional.isEmpty()) {
      userOptional = userRepository.findByEmail(loginData);
      if (userOptional.isEmpty()) {
        response.put("err", "Wrong email or username");
        return response;
      }
    }

    User user = userOptional.get();

    if (passwordConfig.verifyPassword(password, user.getPassword())) {
      Map<String, String> newRefreshToken = refreshTokenManager.generateRefreshToken(user.getId());
      String accessToken = accessTokenManager.GenerateToken(user);
      response.putAll(newRefreshToken);
      response.put("access-token", accessToken);
      return response;
    }

    response.put("err", "Wrong password");
    return response;
  }
}
