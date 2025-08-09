package mensa.plant_my_study.security.authorization.password;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mensa.plant_my_study.main.user.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordService {
  private final ResourceBundleMessageSource messageSource;
  private final GmailEmailService emailService;
  private final UserRepository userRepository;

  public Map<String, String> SendMailResetPassword(final String email, final String language) {
    messageSource.setBasename("lang/messages");
    messageSource.setDefaultEncoding("UTF-8");
    Locale locale = Locale.forLanguageTag(language);
    Map<String, String> response = new HashMap<>();
    try {
      if (userRepository.findByEmail(email).isEmpty()) {
        response.put("err", "No user with this email");
        return response;
      }
      String title = messageSource.getMessage("title", null, locale);
      String mess = messageSource.getMessage("mess", null, locale);
      emailService.sendEmail(email, title, mess);
      response.put("data", "Email sent");
    } catch (Exception e) {
      e.printStackTrace();
      response.put("err", e.toString());
    }
    return response;
  }
}
