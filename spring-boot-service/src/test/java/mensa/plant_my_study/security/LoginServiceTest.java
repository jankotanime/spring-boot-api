package mensa.plant_my_study.security;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import mensa.plant_my_study.security.authorization.login.LoginService;

@SpringBootTest
public class LoginServiceTest {

  @Autowired
  private LoginService loginService;

  @Test
  public void testCase() {
    try {
      Map<String, String> result = loginService.tryToLogin("jasiuuu", "Jasiu1234");
      System.out.println(result.toString());
      Assertions.assertTrue(result.containsKey("access-token"));
    } catch (Exception e) {
      System.err.println("Err" + e.toString());
    }
  }
}
