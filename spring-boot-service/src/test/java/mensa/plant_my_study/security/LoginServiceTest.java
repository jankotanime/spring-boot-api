package mensa.plant_my_study.security;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mensa.plant_my_study.main.manageUser.ManageUserManager;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.authorization.login.LoginService;
import mensa.plant_my_study.security.authorization.register.RegisterService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginServiceTest {

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
  private LoginService loginService;

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

  @ParameterizedTest
  @CsvSource({
    "test1@test.test, Test1234",
    "test1, Test1234"
  })
  public void testLogin(String usernameOrEmail, String password) {
    try {
      Map<String, String> result = loginService.tryToLogin(usernameOrEmail, password);
      Assertions.assertTrue(result.containsKey("access-token") &&
      result.containsKey("refresh-token") && result.containsKey("refresh-token-id"));
    } catch (Exception e) {
      System.err.println("Err" + e.toString());
    }
  }

  @Test
  public void testWrongLogin() {
    try {
      String loginData = "test1@test.tes";
      String password = "Test1234";
      Map<String, String> result = loginService.tryToLogin(loginData, password);
      Assertions.assertFalse(result.containsKey("access-token") ||
      result.containsKey("refresh-token") || result.containsKey("refresh-token-id"));
      Assertions.assertFalse(userRepository.findByEmail(loginData).isPresent());
    } catch (Exception e) {
      System.err.println("Err" + e.toString());
    }
  }

  @Test
  public void testWrongPassword() {
    try {
      String loginData = "test1@test.test";
      String password = "Test12345";
      Map<String, String> result = loginService.tryToLogin(loginData, password);
      Assertions.assertFalse(result.containsKey("access-token") ||
      result.containsKey("refresh-token") || result.containsKey("refresh-token-id"));
      Assertions.assertTrue(userRepository.findByEmail(loginData).isPresent());
    } catch (Exception e) {
      System.err.println("Err" + e.toString());
    }
  }
}
