package mensa.plant_my_study.refreshToken;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

@Component
@RequiredArgsConstructor
public class RefreshTokenOptymalization {
  private final RefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenManager refreshTokenManager;

  @Scheduled(cron="0 5 22 * * *", zone="Europe/Warsaw")
  public void scheduleFixedRateTask() {
    System.out.println("Clearing refresh tokens database");   
    
    Instant now = Instant.now();

    List<RefreshToken> tokens = refreshTokenRepository.findAll();
    List<RefreshToken> tokensToDelete = new ArrayList<>();


    for (RefreshToken token : tokens) {
      if (Duration.between(now, token.getExpiresAt()).isNegative()) {
        tokensToDelete.add(token);
      }
    }

    refreshTokenManager.deleteSomeRefreshTokens(tokensToDelete);
  }
}
