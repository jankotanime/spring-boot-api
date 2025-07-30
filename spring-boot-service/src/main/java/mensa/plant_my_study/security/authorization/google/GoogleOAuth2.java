package mensa.plant_my_study.security.authorization.google;

import com.google.auth.oauth2.UserCredentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class GoogleOAuth2 {
  public class GoogleOAuth2TokenService {
    @Value("${google.id.web}")
    private String clientId;
    @Value("${google.secret.web}")
    private String clientSecret;
    @Value("${google.refresh.web}")
    private String refreshToken;

    public String getAccessToken() throws Exception {
      UserCredentials credentials = UserCredentials.newBuilder()
        .setClientId(clientId)
        .setClientSecret(clientSecret)
        .setRefreshToken(refreshToken)
        .build();
      credentials.refresh();
      return credentials.getAccessToken().getTokenValue();
    }
  }

  @Bean
  public GoogleOAuth2TokenService googleOAuth2TokenService() {
    return new GoogleOAuth2TokenService();
  }
}
