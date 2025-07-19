package mensa.plant_my_study.user;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String username;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(name = "created_at")
  private Instant createdAt;

  public User(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}