package mensa.plant_my_study.security.resetToken;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import mensa.plant_my_study.main.user.User;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reset_tokens")
public class ResetToken {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false)
  private Instant expiresAt;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  private Instant createdAt;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;


  public ResetToken(User user, String token, Instant createdAt, Instant expiresAt) {
    this.user = user;
    this.token = token;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
  }
}
