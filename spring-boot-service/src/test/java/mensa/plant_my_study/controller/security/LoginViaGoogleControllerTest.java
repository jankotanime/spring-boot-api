package mensa.plant_my_study.controller.security;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import mensa.plant_my_study.security.authorization.google.GoogleAuthController;
import mensa.plant_my_study.security.authorization.google.GoogleAuthService;
import mensa.plant_my_study.security.config.SecurityConfig;

@WebMvcTest(GoogleAuthController.class)
@Import(SecurityConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginViaGoogleControllerTest extends BaseWebMvcTest  {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private GoogleAuthService googleAuthService;

  @Test
  void shouldReturnOkForValidLogin() throws Exception {
    Map<String, String> tokens = Map.of(
      "access-token", "access-token",
      "refresh-token", "refresh-token",
      "refresh-token-id", "refresh-token-id"
    );

    when(googleAuthService.tryToLoginWithGoogle("test", "IOS"))
      .thenReturn(tokens);

    String requestBody = "{\"id-token\":\"test\", \"os\":\"IOS\"}";

    mockMvc.perform(post("/google/auth")
      .contentType("application/json")
      .content(requestBody))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.['access-token']").exists())
      .andExpect(jsonPath("$.['refresh-token']").exists())
      .andExpect(jsonPath("$.['refresh-token-id']").exists());
  }
}
