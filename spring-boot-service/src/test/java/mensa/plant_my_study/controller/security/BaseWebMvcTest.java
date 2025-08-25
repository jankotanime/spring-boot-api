package mensa.plant_my_study.controller.security;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import mensa.plant_my_study.main.manageUser.ManageUserManager;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.authorization.register.RegisterService;
import mensa.plant_my_study.security.config.JwtConfig;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseWebMvcTest {
  @MockitoBean
  private RegisterService registerService;

  @MockitoBean
  private JwtConfig jwtConfig;

  @MockitoBean
  private ManageUserManager manageUserManager;

  @MockitoBean
  private UserRepository userRepository;

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

  @BeforeAll
  public void setup() {
    deleteTestUsers();

    registerService.tryToRegister("test1", "test1@test.test", "Test1234");

    User userTest2 = new User("test2", "test2@test.test", null, "test");
    userRepository.save(userTest2);

    when(userRepository.findByUsername("test2"))
      .thenReturn(Optional.of(userTest2));
  }

  @AfterAll
  public void teardown() {
    deleteTestUsers();
  }
}
