package mensa.plant_my_study.config;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.main.manageUser.ManageUserManager;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.refreshToken.RefreshToken;
import mensa.plant_my_study.security.refreshToken.RefreshTokenManager;
import mensa.plant_my_study.security.refreshToken.RefreshTokenRepository;
import mensa.plant_my_study.security.resetToken.ResetToken;
import mensa.plant_my_study.security.resetToken.ResetTokenManager;
import mensa.plant_my_study.security.resetToken.ResetTokenRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;

@Component
@RequiredArgsConstructor
public class DbOptymalization {
  private final RefreshTokenRepository refreshTokenRepository;
  private final RefreshTokenManager refreshTokenManager;
  private final ResetTokenRepository resetTokenRepository;
  private final ResetTokenManager resetTokenManager;
  private final UserRepository userRepository;
  private final ManageUserManager manageUserManager;

  @Scheduled(cron="0 0 0 * * *", zone="Europe/Warsaw")
  public void DailyDeleteFromDB() {
    System.out.println("Clearing refresh tokens from database");

    Instant now = Instant.now();

    List<RefreshToken> refreshTokens = refreshTokenRepository.findAll();
    List<RefreshToken> refreshTokensToDelete = new ArrayList<>();


    for (RefreshToken token : refreshTokens) {
      if (Duration.between(now, token.getExpiresAt()).isNegative()) {
        refreshTokensToDelete.add(token);
      }
    }

    refreshTokenManager.deleteSomeRefreshTokens(refreshTokensToDelete);

    System.out.println("Clearing reset tokens from database");

    List<ResetToken> resetTokens = resetTokenRepository.findAll();
    List<ResetToken> resetTokensToDelete = new ArrayList<>();


    for (ResetToken token : resetTokens) {
      if (Duration.between(now, token.getExpiresAt()).isNegative()) {
        resetTokensToDelete.add(token);
      }
    }

    resetTokenManager.deleteSomeResetTokens(resetTokensToDelete);

    System.out.println("Deleting users from database");

    List<User> users = userRepository.findAll();
    List<User> usersToDelete = new ArrayList<>();


    for (User user : users) {
      if (Duration.between(now, user.getDeleteAt()).isNegative()) {
        usersToDelete.add(user);
      }
    }

    manageUserManager.deleteSomeUsers(usersToDelete);
  }
}
