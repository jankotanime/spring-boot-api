package mensa.plant_my_study.refreshToken;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.accessToken.AccessTokenManager;
import mensa.plant_my_study.user.User;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final AccessTokenManager accessTokenManager;
  private final RefreshTokenManager refreshTokenManager;
  public Map<String, String> refreshRefreshToken(final String token) {
    System.out.println("Przed serwis");
    Map<String, String> response = new HashMap<>();
    
    User validateUser = refreshTokenManager.validateRefreshTokenAndGetUser(token);
    
    if (validateUser == null) {
      response.put("err", "Expired refresh token");
      return response;
    }

    System.out.println("Po validate");
    refreshTokenManager.deleteRefreshToken(token);
    
    String newRefreshToken = refreshTokenManager.generateRefreshToken(validateUser.getId());
    String newAccessToken = accessTokenManager.GenerateToken(validateUser);
    System.out.println("Przed response");
    response.put("refresh-token", newRefreshToken);
    response.put("access-token", newAccessToken);
    return response;
  }
}
