package mensa.plant_my_study.controller.security;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import mensa.plant_my_study.main.manageUser.ManageUserManager;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.accessToken.AccessTokenManager;
import mensa.plant_my_study.security.authorization.login.LoginController;
import mensa.plant_my_study.security.authorization.login.LoginService;
import mensa.plant_my_study.security.authorization.register.RegisterService;
import mensa.plant_my_study.security.config.JwtConfig;
import mensa.plant_my_study.security.config.SecurityConfig;
import mensa.plant_my_study.security.refreshToken.RefreshTokenManager;
import mensa.plant_my_study.security.refreshToken.RefreshTokenRepository;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Map;
import java.util.Optional;

@WebMvcTest(LoginController.class)
@Import(SecurityConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtConfig jwtConfig;

    @MockBean
    private LoginService loginService;

    @MockBean
    private RegisterService registerService;

    @MockBean
    private RefreshTokenManager refreshTokenManager;

    @MockBean
    private AccessTokenManager accessTokenManager;

    @MockBean
    private ManageUserManager manageUserManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

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
  }

  @AfterAll
  public void teardown() {
    deleteTestUsers();
  }

    @Test
void shouldReturnOkForValidLogin() throws Exception {
    Map<String, String> tokens = Map.of(
        "access-token", "access-token",
        "refresh-token", "refresh-token",
        "refresh-token-id", "refresh-token-id"
    );

    when(loginService.tryToLogin("test1", "Test1234"))
        .thenReturn(tokens);

    String requestBody = "{\"login-data\":\"test1\", \"password\":\"Test1234\"}";

    mockMvc.perform(post("/login")
            .contentType("application/json")
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.['access-token']").exists())
        .andExpect(jsonPath("$.['refresh-token']").exists())
        .andExpect(jsonPath("$.['refresh-token-id']").exists());
}


    @Test
    void shouldReturnBadRequestForMissingLoginData() throws Exception {
        String requestBody = "{\"password\":\"Test1234\"}";

        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"err\":\"Bad request\"}"));
    }

    @Test
    void shouldReturnBadRequestForMissingPassword() throws Exception {
        String requestBody = "{\"login-data\":\"test1\"}";

        mockMvc.perform(post("/login")
                .contentType("application/json")
                .content(requestBody))
            .andExpect(status().isBadRequest())
            .andExpect(content().json("{\"err\":\"Bad request\"}"));
    }
}
