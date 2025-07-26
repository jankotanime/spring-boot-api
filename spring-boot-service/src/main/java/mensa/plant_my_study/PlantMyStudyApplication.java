package mensa.plant_my_study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableScheduling
@EnableWebSecurity
public class PlantMyStudyApplication {
  public static void main(String[] args) {
    SpringApplication.run(PlantMyStudyApplication.class, args);
  }
}
