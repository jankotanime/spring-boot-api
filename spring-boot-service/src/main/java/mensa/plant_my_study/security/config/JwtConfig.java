package mensa.plant_my_study.security.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import jakarta.annotation.PostConstruct;
import mensa.plant_my_study.security.jwtAuthorization.JwtPrincipal;

@Component
public class JwtConfig {
  @Value("${jwt.secret}")
  private String secretPath;

  private String secretKey;

  @PostConstruct
  public void init() throws IOException {
    secretKey = Files.readString(Path.of(secretPath)).trim();
  }

  public String getSecretKey() {
    return secretKey;
  }

  public String createToken(UUID id, String username) {
    String token = JWT.create()
      .withSubject(id.toString())
      .withClaim("username", username)
      .withExpiresAt(new Date(System.currentTimeMillis() + (5 * 60 * 1000)))
      .sign(Algorithm.HMAC256(secretKey));
    return token;
  }

  public String getUsernameFromJWT() {
    String username = ((JwtPrincipal) SecurityContextHolder.getContext()
      .getAuthentication().getPrincipal()).getUsername();
    return username;
  }
}
