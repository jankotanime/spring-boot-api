package mensa.plant_my_study.main.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(UUID id);
  Optional<User> findByUsername(String username);
  Optional<User> findByEmail(String email);
}
