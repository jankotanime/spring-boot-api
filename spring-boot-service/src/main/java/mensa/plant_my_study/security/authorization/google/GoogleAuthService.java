package mensa.plant_my_study.security.authorization.google;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

import lombok.extern.slf4j.Slf4j;
import mensa.plant_my_study.security.accessToken.AccessTokenManager;
import mensa.plant_my_study.security.refreshToken.RefreshTokenManager;
import mensa.plant_my_study.user.User;
import mensa.plant_my_study.user.UserRepository;

@Service
@Slf4j
public class GoogleAuthService {
  private final UserRepository userRepository;
  private final RefreshTokenManager refreshTokenManager;
  private final AccessTokenManager accessTokenManager;
  private final GoogleIdTokenVerifier verifier;

  public GoogleAuthService(UserRepository userRepository, RefreshTokenManager refreshTokenManager,
    AccessTokenManager accessTokenManager, @Value("${google.id}") String googleId) {
    this.userRepository = userRepository;
    this.refreshTokenManager = refreshTokenManager;
    this.accessTokenManager = accessTokenManager;
    this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
      new GsonFactory()).setAudience(Collections.singletonList(googleId)).build();
  }

  public Map<String, String> tryToLoginWithGoogle(String idTokenString) {
    Map<String, String> response = new HashMap<>();
    GoogleIdToken idToken;

    try {
      idToken = verifier.verify(idTokenString);
    } catch (GeneralSecurityException | IOException e) {
      response.put("err", "Verify error");
      return response;
    }

    if (idToken == null) {
      response.put("err", "Verify error");
      return response;
    }

    GoogleIdToken.Payload payload = idToken.getPayload();
    String googleId = payload.getSubject();
    String email = payload.getEmail();
    String username = (String) payload.get("name");

    Optional<User> userOptional = userRepository.findByEmail(email);

    if (userOptional.isEmpty()) {
      User user = new User(username, email, null, googleId);
      userRepository.save(user);
      Map<String, String> newRefreshToken = refreshTokenManager.generateRefreshToken(user.getId());
      String accessToken = accessTokenManager.GenerateToken(user);
      response.putAll(newRefreshToken);
      response.put("access-token", accessToken);
    }

    User user = userOptional.get();

    if (user.getGoogleId() == null) {
      user.setGoogleId(googleId);
      userRepository.save(user);
    }

    if (!user.getGoogleId().equals(googleId)) {
      response.put("err", "Wrong google id");
      return response;
    }

    Map<String, String> newRefreshToken = refreshTokenManager.generateRefreshToken(user.getId());
    String accessToken = accessTokenManager.GenerateToken(user);
    response.putAll(newRefreshToken);
    response.put("access-token", accessToken);

    return response;
  }
}
