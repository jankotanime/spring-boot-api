package mensa.plant_my_study.security.accessToken;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mensa.plant_my_study.security.refreshToken.RefreshTokenManager;
import mensa.plant_my_study.user.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessTokenService {
  private final RefreshTokenManager refreshTokenManager;
  private final AccessTokenManager accessTokenLogic;

  public Map<String, String> GetToken(UUID refreshTokenId, String refreshToken) {
    Map<String, String> response = new HashMap<>();

    User validateUser = refreshTokenManager.validateRefreshTokenAndGetUser(refreshTokenId, refreshToken);

    if (validateUser == null) {
      response.put("err", "Expired refresh token");
      return response;
    }

    String token = accessTokenLogic.GenerateToken(validateUser);

    response.put("access-token", token);
    return response;
  }
}
