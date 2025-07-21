package mensa.plant_my_study.authorization.logout;

import mensa.plant_my_study.refreshToken.RefreshToken;
import mensa.plant_my_study.refreshToken.RefreshTokenManager;
import mensa.plant_my_study.refreshToken.RefreshTokenRepository;
import mensa.plant_my_study.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService {
  private final RefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenManager refreshTokenManager;

  public Map<String, String> tryToLogout(final UUID tokenId, final String token) {
    Map<String, String> response = new HashMap<>();

    User validateUser = refreshTokenManager.validateRefreshTokenAndGetUser(tokenId, token);

    if (validateUser == null) {
      response.put("err", "Expired refresh token");
      return response;
    }

    Boolean deletedToken = refreshTokenManager.deleteRefreshToken(tokenId);

    if (!deletedToken) {
      response.put("err", "Error while deleting token");
      return response;
    }

    response.put("data", "Logout success");
    return response;
  }

  public Map<String, String> tryToLogoutFromAllDevices(final UUID tokenId, final String token) {
    Map<String, String> response = new HashMap<>();

    User validateUser = refreshTokenManager.validateRefreshTokenAndGetUser(tokenId, token);

    if (validateUser == null) {
      response.put("err", "Expired refresh token");
      return response;
    }

    List<RefreshToken> allTokens = refreshTokenRepository.findAllByUser(validateUser);

    refreshTokenManager.deleteSomeRefreshTokens(allTokens);

    response.put("data", "Delete success");
    return response;
  }
}
