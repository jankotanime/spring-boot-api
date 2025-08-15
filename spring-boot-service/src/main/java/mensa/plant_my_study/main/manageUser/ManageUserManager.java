package mensa.plant_my_study.main.manageUser;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;

@RequiredArgsConstructor
@Component
public class ManageUserManager {
  private final UserRepository userRepository;
  private static final Duration DELETION_TIME = Duration.ofDays(30);

  public void createUser(User user) {
    userRepository.save(user);
  }

  public void startDeletingUser(User user) {
    Instant now = Instant.now();
    user.setDeleteAt(now.plus(DELETION_TIME));
  }

  public void deleteSomeUsers(List<User> users) {
    for (User user : users) {
      userRepository.delete(user);
    }
  }
}
