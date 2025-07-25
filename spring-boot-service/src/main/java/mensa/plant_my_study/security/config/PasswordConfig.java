package mensa.plant_my_study.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  public boolean verifyPassword(String rawPassword, String encodedPassword) {
    return passwordEncoder().matches(rawPassword, encodedPassword);
  }
}
