package mensa.plant_my_study.accessToken;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.security.JwtConfig;
import mensa.plant_my_study.user.User;

@RequiredArgsConstructor
@Component
public class AccessTokenManager {
  private final JwtConfig jwtConfig;

  public String GenerateToken(User user) {
    String token = jwtConfig.createToken(user.getId(), user.getUsername());
    
    return token;
  }
}
