package mensa.plant_my_study.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final JwtConfig jwtConfig;

  @Autowired
  public SecurityConfig(JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  Boolean jwtEnabled = true; // ! Only for dev

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    String secretKey = jwtConfig.getSecretKey();
    
    if (jwtEnabled) {
      http.authorizeHttpRequests(auth -> auth
        .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
        .anyRequest().authenticated()
      );
    } else {
      http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    }
    http
      .csrf(AbstractHttpConfigurer::disable)
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .addFilterBefore(new JwtAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
