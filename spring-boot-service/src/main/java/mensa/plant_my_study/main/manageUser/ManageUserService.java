package mensa.plant_my_study.main.manageUser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

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

  public static boolean patternMatches(String emailAddress, String regexPattern) {
    return Pattern.compile(regexPattern)
      .matcher(emailAddress)
      .matches();
  }

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

  public Map<String, String> changeEmail(String username, String email) {
    Map<String, String> response = new HashMap<>();
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isEmpty()) {
      response.put("err", "Wrong username");
      return response;
    }

    if (!patternMatches(email, "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+.[a-zA-Z0-9.-]+$")) {
      response.put("err", "Wrong email");
      return response;
    }

    User user = userOptional.get();

    Boolean change = manageUserManager.changeEmail(user, email);

    if (change) {
      response.put("data", "Email changed");
      return response;
    }
    response.put("err", "Email taken");
    return response;
  }

  public Map<String, String> changeUsername(String username, String newUsername) {
    Map<String, String> response = new HashMap<>();
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isEmpty()) {
      response.put("err", "Wrong username");
      return response;
    }

    User user = userOptional.get();

    Boolean change = manageUserManager.changeUsername(user, newUsername);

    if (change) {
      response.put("data", "Username changed");
      return response;
    }
    response.put("err", "Username taken");
    return response;
  }
}
