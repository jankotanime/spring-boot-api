package mensa.plant_my_study.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

  private final String secretKey;

  public JwtAuthorizationFilter(String secretKey) {
    this.secretKey = secretKey;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain) throws ServletException, IOException {
    String accessToken = request.getHeader("Authorization");

    if (accessToken != null && accessToken.startsWith("Bearer ")) {
      accessToken = accessToken.substring(7);
      try {
        DecodedJWT jwt = JWT.require(com.auth0.jwt.algorithms.Algorithm.HMAC256(secretKey)).build().verify(accessToken);
        String id = jwt.getSubject();      
        String username = jwt.getClaim("username").asString();
        if (id != null && username != null) {
          JwtPrincipal principal = new JwtPrincipal(id, username);
          UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(principal, null, null);
          SecurityContextHolder.getContext().setAuthentication(authentication);
        }
      } catch (JWTVerificationException e) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
        return;
      }
    }
    filterChain.doFilter(request, response);
  }
}