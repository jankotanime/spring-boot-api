package mensa.plant_my_study.security.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import mensa.plant_my_study.security.jwtAuthorization.JwtAuthorizationFilter;

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
        .requestMatchers(HttpMethod.OPTIONS, "/password/set/mail").permitAll()
        .requestMatchers(HttpMethod.POST, "/login", "/register", "/google/auth",
        "/access-token", "/refresh-token", "/send-mail/password/reset", "/send-mail/password/set",
        "/password/set/mail", "/test").permitAll()
        .requestMatchers(HttpMethod.GET, "/actuator/health").permitAll()
        .anyRequest().authenticated());
    } else {
      http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    }
    http.cors(cors -> cors.configurationSource(request -> {
      var corsConfig = new org.springframework.web.cors.CorsConfiguration();
      corsConfig.addAllowedOrigin("http://localhost:3000");
      corsConfig.addAllowedMethod("POST");
      corsConfig.addAllowedHeader("*");
      corsConfig.setAllowCredentials(true);
      return corsConfig;
    }))
      .exceptionHandling(eh -> eh
        .authenticationEntryPoint((request, response, authException) -> {
          response.setStatus(401);
          response.setContentType("application/json");
          response.setCharacterEncoding("UTF-8");
          Map<String, Object> errorBody = Map.of(
            "err", "Token error"
          );
          new ObjectMapper().writeValue(response.getWriter(), errorBody);
          response.getWriter().flush();
        })
      )
      .csrf(AbstractHttpConfigurer::disable)
      .logout(logout -> logout.disable())
      .formLogin(AbstractHttpConfigurer::disable)
      .httpBasic(AbstractHttpConfigurer::disable)
      .addFilterBefore(new JwtAuthorizationFilter(secretKey), UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
