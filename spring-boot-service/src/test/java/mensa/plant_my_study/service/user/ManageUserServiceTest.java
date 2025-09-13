package mensa.plant_my_study.service.user;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mensa.plant_my_study.main.manageUser.ManageUserManager;
import mensa.plant_my_study.main.manageUser.ManageUserService;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.authorization.register.RegisterService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ManageUserServiceTest {
  public void deleteTestUsers() {
    Optional<User> userEmailTest1 = userRepository.findByEmail("test1@test.test");
    if (userEmailTest1.isPresent()) {
      manageUserManager.deleteUser(userEmailTest1.get());
    }

    Optional<User> userUsernameTest1 = userRepository.findByUsername("test1");
    if (userUsernameTest1.isPresent()) {
      manageUserManager.deleteUser(userUsernameTest1.get());
    }

    Optional<User> userEmailTest2 = userRepository.findByEmail("test2@test.test");
    if (userEmailTest2.isPresent()) {
      manageUserManager.deleteUser(userEmailTest2.get());
    }

    Optional<User> userUsernameTest2 = userRepository.findByUsername("test2");
    if (userUsernameTest2.isPresent()) {
      manageUserManager.deleteUser(userUsernameTest2.get());
    }
  }

  @Autowired
  private ManageUserService manageUserService;

  @Autowired
  private RegisterService registerService;

  @Autowired
  private ManageUserManager manageUserManager;

  @Autowired
  private UserRepository userRepository;

  @BeforeAll
  public void setup() {
    deleteTestUsers();

    registerService.tryToRegister("test1", "test1@test.test", "Test1234");
  }

  @AfterAll
  public void teardown() {
    deleteTestUsers();
  }

  private User getTestUser(String username) {
    return userRepository.findByUsername(username).get();
  }

  @Test
  public void testStartDeleteUser() {
    String username = "test1";
    try {
      Assertions.assertNull(getTestUser(username).getDeleteAt());
      manageUserService.startDeletingUser(username);
      Assertions.assertNotNull(getTestUser(username).getDeleteAt());
    } catch (Exception e) {
      System.err.println("Err" + e.toString());
    }
  }

  @Test
  public void changeEmail() {
    String username = "test1";
    String email = "test2@test.test";
    try {
      Assertions.assertNotEquals(getTestUser(username).getEmail(), email);
      manageUserService.changeEmail(username, email);
      Assertions.assertEquals(getTestUser(username).getEmail(), email);
    } catch (Exception e) {
      System.err.println("Err" + e.toString());
    }
  }

  @Test
  public void changeUsername() {
    String username = "test1";
    String newUsername = "test2";
    try {
      Map<String, String> result = manageUserService.changeUsername(username, newUsername);
      Assertions.assertTrue(result.containsKey("data"));
    } catch (Exception e) {
      System.err.println("Err" + e.toString());
    }
  }
}
