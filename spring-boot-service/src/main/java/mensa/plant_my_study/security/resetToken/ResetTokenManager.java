package mensa.plant_my_study.security.resetToken;

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
import mensa.plant_my_study.security.config.ResetTokenConfig;
import java.security.SecureRandom;
import java.util.Base64;

@RequiredArgsConstructor
@Component
public class ResetTokenManager {
  private final UserRepository userRepository;
  private final ResetTokenRepository resetTokenRepository;
  private final ResetTokenConfig tokenConfig;
  private static final Duration RESET_TOKEN_VALIDITY = Duration.ofMinutes(20);

public String generateLongResetToken() {
    SecureRandom secureRandom = new SecureRandom();
    byte[] tokenBytes = new byte[32];
    secureRandom.nextBytes(tokenBytes);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
}

  public Map<String, String> generateResetToken(final UUID userId) {
    Map<String, String> result = new HashMap<>();
    final User user = userRepository.findById(userId).orElseThrow();
    final String newToken = generateLongResetToken();
    String hashedToken = tokenConfig.tokenEncoder().encode(newToken);
    final Instant createdAt = Instant.now();
    final Instant expiresAt = createdAt.plus(RESET_TOKEN_VALIDITY);
    final ResetToken resetToken = new ResetToken(user, hashedToken, createdAt, expiresAt);
    resetTokenRepository.save(resetToken);
    result.put("reset-token-id", resetToken.getId().toString());
    result.put("reset-token", newToken);
    return result;
  }

  public Boolean deleteResetToken(final UUID tokenId) {
    Optional<ResetToken> resetTokenOptional = resetTokenRepository.findById(tokenId);

    if (resetTokenOptional.isEmpty()) {
      return false;
    }

    ResetToken resetToken = resetTokenOptional.get();

    resetTokenRepository.delete(resetToken);

    return true;
  }

  public void deleteSomeResetTokens(final List<ResetToken> tokens) {
    for (ResetToken token : tokens) {
      resetTokenRepository.delete(token);
    }
  }

  public User validateResetTokenAndGetUser(final UUID tokenId, final String token) {
    Instant now = Instant.now();
    Optional<ResetToken> resetTokenOptional = resetTokenRepository.findById(tokenId);

    if (resetTokenOptional.isEmpty()) {
      return null;
    }

    ResetToken resetToken = resetTokenOptional.get();

    if (Duration.between(now, resetToken.getExpiresAt()).isNegative()) {
      deleteResetToken(resetToken.getId());
      return null;
    }
    if (!tokenConfig.verifyToken(token, resetToken.getToken())) {
      return null;
    }

    deleteResetToken(resetToken.getId());
    return resetToken.getUser();
  }
}
