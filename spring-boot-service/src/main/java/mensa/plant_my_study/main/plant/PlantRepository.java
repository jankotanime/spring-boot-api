package mensa.plant_my_study.main.plant;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
  Optional<Plant> findById(UUID id);
}
