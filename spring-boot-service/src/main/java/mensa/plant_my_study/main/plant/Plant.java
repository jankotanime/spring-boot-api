package mensa.plant_my_study.main.plant;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "plants")
public class Plant {
  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Integer cost;

  @Column(name = "max_level", nullable = false)
  private Integer maxLevel;

  @Column(name = "minutes_to_level_up", nullable = false)
  private Integer minutesToLevelUp;
}
