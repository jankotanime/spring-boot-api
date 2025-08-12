package mensa.plant_my_study.main.studySession;

import java.time.Instant;
import java.util.UUID;

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
@Table(name = "study_sessions")
  public class StudySession {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(name = "time_min")
  private Integer timeMin;

  @Column(name = "session_end_at")
  private Instant sessionEndAt;

  public StudySession(User user, Integer timeMin, Instant sessionEndAt) {
    this.user = user;
    this.timeMin = timeMin;
    this.sessionEndAt = sessionEndAt;
  }
}
