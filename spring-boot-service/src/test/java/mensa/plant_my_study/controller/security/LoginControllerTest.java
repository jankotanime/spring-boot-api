package mensa.plant_my_study.controller.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import mensa.plant_my_study.security.authorization.login.LoginController;
import mensa.plant_my_study.security.authorization.login.LoginService;
import mensa.plant_my_study.security.config.SecurityConfig;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Map;

@WebMvcTest(LoginController.class)
@Import(SecurityConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginControllerTest extends BaseWebMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private LoginService loginService;

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
  void shouldReturnConflictForAccountWithNoPassword() throws Exception {
    String requestBody = "{\"login-data\":\"test2\", \"password\":\"Test1234\"}";

    when(loginService.tryToLogin(anyString(), anyString()))
      .thenAnswer(invocation -> {
        String login = invocation.getArgument(0);
        String pass = invocation.getArgument(1);
        if ("test2".equals(login) && "Test1234".equals(pass)) {
            return Map.of("err", "Password not set");
        }
        return Map.of();
      }
    );

    mockMvc.perform(post("/login")
      .contentType("application/json")
      .content(requestBody))
      .andExpect(status().isConflict())
      .andExpect(content().json("{\"err\":\"Password not set\"}"));
  }

  @Test
  void shouldReturnUnauthorizedForWrongPassword() throws Exception {
    String requestBody = "{\"login-data\":\"test1\", \"password\":\"Test1235\"}";

    when(loginService.tryToLogin(anyString(), anyString()))
      .thenAnswer(invocation -> {
        String login = invocation.getArgument(0);
        String pass = invocation.getArgument(1);
        if ("test1".equals(login) && "Test1235".equals(pass)) {
            return Map.of("err", "Wrong password");
        }
        return Map.of();
      }
    );

    mockMvc.perform(post("/login")
      .contentType("application/json")
      .content(requestBody))
      .andExpect(status().isUnauthorized())
      .andExpect(content().json("{\"err\":\"Wrong password\"}"));
  }

  @Test
  void shouldReturnUnauthorizedForUsernameOrEmail() throws Exception {
    String requestBody = "{\"login-data\":\"test3\", \"password\":\"Test1234\"}";

    when(loginService.tryToLogin(anyString(), anyString()))
      .thenAnswer(invocation -> {
        String login = invocation.getArgument(0);
        String pass = invocation.getArgument(1);
        if ("test3".equals(login) && "Test1234".equals(pass)) {
            return Map.of("err", "Wrong email or username");
        }
        return Map.of();
      }
    );

    mockMvc.perform(post("/login")
      .contentType("application/json")
      .content(requestBody))
      .andExpect(status().isUnauthorized())
      .andExpect(content().json("{\"err\":\"Wrong email or username\"}"));
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
