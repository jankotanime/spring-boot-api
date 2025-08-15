package mensa.plant_my_study.main.manageUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManageUserService {
  private final ManageUserManager manageUserManager;
  private final UserRepository userRepository;

  public Map<String, String> StartDeletingUser(String username) {
    Map<String, String> response = new HashMap<>();
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isEmpty()) {
      response.put("err", "Wrong username");
      return response;
    }

    User user = userOptional.get();

    if (user.getDeleteAt() != null) {
      response.put("err", "User already Disactivated");
      return response;
    }

    manageUserManager.startDeletingUser(user);
    response.put("data", "User activated to delete");
    return response;
  }
}
