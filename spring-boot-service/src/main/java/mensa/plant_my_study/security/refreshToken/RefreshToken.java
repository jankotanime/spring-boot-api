package mensa.plant_my_study.security.refreshToken;

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
import lombok.*;
import mensa.plant_my_study.main.user.User;

@Data
@NoArgsConstructor
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String token;

  @Column(nullable = false, updatable = false)
  @CreatedDate
  private Instant createdAt;

  @Column(nullable = false)
  private Instant expiresAt;

  public RefreshToken(User user, String token, Instant createdAt, Instant expiresAt) {
    this.user = user;
    this.token = token;
    this.createdAt = createdAt;
    this.expiresAt = expiresAt;
  }
}