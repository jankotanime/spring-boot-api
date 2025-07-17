package mensa.plant_my_study.security;

import lombok.Getter;

@Getter
public class JwtPrincipal {
  private final String id;
  private final String username;

  public JwtPrincipal(String id, String username) {
      this.id = id;
      this.username = username;
  }
}
