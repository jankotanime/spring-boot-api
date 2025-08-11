package mensa.plant_my_study.security.password;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mensa.plant_my_study.config.EmailService;
import mensa.plant_my_study.main.user.User;
import mensa.plant_my_study.main.user.UserRepository;
import mensa.plant_my_study.security.resetToken.ResetTokenManager;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordEmailService {
  private final ResourceBundleMessageSource messageSource;
  private final EmailService emailService;
  private final UserRepository userRepository;
  private final ResetTokenManager resetTokenManager;

  @Value("${website.url}")
  private String websiteUrl;

  public Map<String, String> SendMailResetPassword(final String email, final String language) {
    messageSource.setBasename("lang/messages");
    messageSource.setDefaultEncoding("UTF-8");
    Locale locale = Locale.forLanguageTag(language);
    Map<String, String> response = new HashMap<>();
    try {
      Optional<User> userOptional = userRepository.findByEmail(email);
      if (userOptional.isEmpty()) {
        response.put("err", "No user with this email");
        return response;
      }

      User user = userOptional.get();

      Map<String, String> resetTokenData = resetTokenManager.generateResetToken(user.getId());
      String resetTokenId = resetTokenData.get("reset-token-id");
      String resetToken = resetTokenData.get("reset-token");

      String title = messageSource.getMessage("title-reset-password", null, locale);
      String starting = messageSource.getMessage("start-reset-password", null, locale);
      String ending = messageSource.getMessage("end-reset-password", null, locale);

      String mess = starting + websiteUrl + "/reset-password?token-id=" + resetTokenId + "&token=" + resetToken + ending;
      emailService.sendEmail(email, title, mess);
      response.put("data", "Email sent");
    } catch (Exception e) {
      e.printStackTrace();
      response.put("err", e.toString());
    }
    return response;
  }

  public Map<String, String> SendMailSetPassword(final String email, final String language) {
    messageSource.setBasename("lang/messages");
    messageSource.setDefaultEncoding("UTF-8");
    Locale locale = Locale.forLanguageTag(language);
    Map<String, String> response = new HashMap<>();
    try {
      Optional<User> userOptional = userRepository.findByEmail(email);
      if (userOptional.isEmpty()) {
        response.put("err", "No user with this email");
        return response;
      }

      User user = userOptional.get();

      if (user.getPassword() != null) {
        response.put("err", "User has password");
        return response;
      }

      Map<String, String> resetTokenData = resetTokenManager.generateResetToken(user.getId());
      String resetTokenId = resetTokenData.get("reset-token-id");
      String resetToken = resetTokenData.get("reset-token");

      String title = messageSource.getMessage("title-set-password", null, locale);
      String starting = messageSource.getMessage("start-set-password", null, locale);
      String ending = messageSource.getMessage("end-set-password", null, locale);

      String mess = starting + websiteUrl + "/set-password?token-id=" + resetTokenId + "&token=" + resetToken + ending;
      emailService.sendEmail(email, title, mess);
      response.put("data", "Email sent");
    } catch (Exception e) {
      e.printStackTrace();
      response.put("err", e.toString());
    }
    return response;
  }
}
