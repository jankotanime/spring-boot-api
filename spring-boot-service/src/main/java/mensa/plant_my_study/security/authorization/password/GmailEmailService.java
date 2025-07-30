package mensa.plant_my_study.security.authorization.password;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import mensa.plant_my_study.security.authorization.google.GoogleOAuth2.GoogleOAuth2TokenService;

import org.eclipse.angus.mail.smtp.SMTPTransport;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Properties;

@Service
public class GmailEmailService {

  private final GoogleOAuth2TokenService tokenService;

  public GmailEmailService(GoogleOAuth2TokenService tokenService) {
    this.tokenService = tokenService;
  }

  private static String buildOAuth2Token(String email, String accessToken) {
    String raw = "user=" + email + "\001auth=Bearer " + accessToken + "\001\001";
    return Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
  }

  public void sendEmail(String to, String subject, String body) throws Exception {
    String accessToken = tokenService.getAccessToken();
    String email = "plantmystudy@gmail.com";

    Properties props = new Properties();
    props.put("mail.smtp.starttls.enable", "true");
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.debug", "true");


    Session session = Session.getInstance(props);
    MimeMessage message = new MimeMessage(session);
    message.setFrom(new InternetAddress(email));
    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
    message.setSubject(subject);
    message.setText(body);

    SMTPTransport smtpTransport = (SMTPTransport) session.getTransport("smtp");

    smtpTransport.connect("smtp.gmail.com", 587, null, null);

    smtpTransport.issueCommand("AUTH XOAUTH2 " + buildOAuth2Token(email, accessToken), 235);

    smtpTransport.sendMessage(message, message.getAllRecipients());
    smtpTransport.close();
  }
}