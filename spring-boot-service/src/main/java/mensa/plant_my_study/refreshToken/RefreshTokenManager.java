package mensa.plant_my_study.refreshToken;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import mensa.plant_my_study.user.User;
import mensa.plant_my_study.user.UserRepository;

@RequiredArgsConstructor
@Component
public class RefreshTokenManager {
  private final UserRepository userRepository;
  private final RefreshTokenRepository refreshTokenRepository;
  private static final Duration REFRESH_TOKEN_VALIDITY = Duration.ofMinutes(60);

  public String generateRefreshToken(final UUID userId) {
    final User user = userRepository.findById(userId).orElseThrow();
    final String newToken = UUID.randomUUID().toString();
    final Instant createdAt = Instant.now();
    final Instant expiresAt = createdAt.plus(REFRESH_TOKEN_VALIDITY);
    final RefreshToken refreshToken = new RefreshToken(user, newToken, createdAt, expiresAt);
    refreshTokenRepository.save(refreshToken);
    return newToken;
  }

  public Boolean deleteRefreshToken(final String token) {
    Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByToken(token);

    if (refreshTokenOptional.isEmpty()) {
      return false;
    }

    RefreshToken refreshToken = refreshTokenOptional.get();
    
    refreshTokenRepository.delete(refreshToken);

    return true;
  }

  public User validateRefreshTokenAndGetUser(final String token) {
    Instant now = Instant.now();
    Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByToken(token);

    if (refreshTokenOptional.isEmpty()) {
      return null;
    }

    RefreshToken refreshToken = refreshTokenOptional.get();
    if (Duration.between(now, refreshToken.getExpiresAt()).isNegative()) {
      deleteRefreshToken(refreshToken.getToken());
      return null;
    }

    return refreshToken.getUser();
  }
}
