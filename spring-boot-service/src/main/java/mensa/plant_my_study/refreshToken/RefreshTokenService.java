package mensa.plant_my_study.refreshToken;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.accessToken.AccessTokenManager;
import mensa.plant_my_study.user.User;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
  private final AccessTokenManager accessTokenManager;
  private final RefreshTokenManager refreshTokenManager;

  public Map<String, String> refreshRefreshToken(final UUID tokenId, final String token) {
    Map<String, String> response = new HashMap<>();
    
    User validateUser = refreshTokenManager.validateRefreshTokenAndGetUser(tokenId, token);
    
    if (validateUser == null) {
      response.put("err", "Expired refresh token");
      return response;
    }

    refreshTokenManager.deleteRefreshToken(tokenId);
    
    Map<String, String> newRefreshToken = refreshTokenManager.generateRefreshToken(validateUser.getId());
    String newAccessToken = accessTokenManager.GenerateToken(validateUser);
    response.putAll(newRefreshToken);
    response.put("access-token", newAccessToken);
    return response;
  }
}
