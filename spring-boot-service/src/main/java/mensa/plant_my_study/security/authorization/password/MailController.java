package mensa.plant_my_study.security.authorization.password;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {

  private final GmailEmailService emailService;

  public MailController(GmailEmailService emailService) {
    this.emailService = emailService;
  }

  @PostMapping("/send")
  public String sendMail(@RequestBody Map<String, String> reqData) {
    String to = reqData.get("to");
    try {
      emailService.sendEmail(to, "Plant My Study Test", "Hello from Spring Boot");
      return "Mail sent!";
    } catch (Exception e) {
      e.printStackTrace();
      return "Błąd: " + e.getMessage();
    }
  }
}
