package mensa.plant_my_study.security.refreshToken;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.config.RefreshTokenConfig;

@RequiredArgsConstructor
@Component
public class RefreshTokenManager {
  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenConfig tokenConfig;
  private static final Duration REFRESH_TOKEN_VALIDITY = Duration.ofDays(60);

  public Map<String, String> generateRefreshToken(final UUID userId) {
    Map<String, String> result = new HashMap<>();
    final User user = userRepository.findById(userId).orElseThrow();
    final String newToken = UUID.randomUUID().toString();
    String hashedToken = tokenConfig.tokenEncoder().encode(newToken);
    final Instant createdAt = Instant.now();
    final Instant expiresAt = createdAt.plus(REFRESH_TOKEN_VALIDITY);
    final RefreshToken refreshToken = new RefreshToken(user, hashedToken, createdAt, expiresAt);
    refreshTokenRepository.save(refreshToken);
    result.put("refresh-token-id", refreshToken.getId().toString());
    result.put("refresh-token", newToken);
    return result;
  }

  public Boolean deleteRefreshToken(final UUID tokenId) {
    Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(tokenId);

    if (refreshTokenOptional.isEmpty()) {
      return false;
    }

    RefreshToken refreshToken = refreshTokenOptional.get();

    refreshTokenRepository.delete(refreshToken);

    return true;
  }

  public void deleteSomeRefreshTokens(final List<RefreshToken> tokens) {
    for (RefreshToken token : tokens) {
      refreshTokenRepository.delete(token);
    }
  }

  public User validateRefreshTokenAndGetUser(final UUID tokenId, final String token) {
    Instant now = Instant.now();
    Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findById(tokenId);

    if (refreshTokenOptional.isEmpty()) {
      return null;
    }

    RefreshToken refreshToken = refreshTokenOptional.get();

    if (Duration.between(now, refreshToken.getExpiresAt()).isNegative()) {
      deleteRefreshToken(refreshToken.getId());
      return null;
    }

    if (!tokenConfig.verifyToken(token, refreshToken.getToken())) {
      return null;
    }

    return refreshToken.getUser();
  }
}
