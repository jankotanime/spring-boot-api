package mensa.plant_my_study.accessToken;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mensa.plant_my_study.refreshToken.RefreshTokenManager;
import mensa.plant_my_study.user.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccessTokenService {
  private final RefreshTokenManager refreshTokenManager;
  private final AccessTokenManager accessTokenLogic;

  public Map<String, String> GetToken(String refreshToken) {
    Map<String, String> response = new HashMap<>();

    User validateUser = refreshTokenManager.validateRefreshTokenAndGetUser(refreshToken);

    if (validateUser == null) {
      response.put("err", "Expired refresh token");
      return response;
    }

    String token = accessTokenLogic.GenerateToken(validateUser);

    response.put("access-token", token);
    return response;
  }
}
