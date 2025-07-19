package mensa.plant_my_study.security;

import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtConfig {
  @Value("${jwt.secret}")
  private String secretKey;

  public String getSecretKey() {
    return secretKey;
  }

  public String createToken(UUID id, String username) {
    String token = JWT.create()
      .withSubject(id.toString())
      .withClaim("username", username)
      .withExpiresAt(new Date(System.currentTimeMillis() + 3600000))
      .sign(Algorithm.HMAC256(secretKey));
    return token;
  }
}
