package mensa.plant_my_study.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class RefreshTokenConfig {
  public PasswordEncoder tokenEncoder() {
    return new BCryptPasswordEncoder();
  }

  public boolean verifyToken(String rawToken, String encodedToken) {
    return tokenEncoder().matches(rawToken, encodedToken);
  }
}
