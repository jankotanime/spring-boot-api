package mensa.plant_my_study.refreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mensa.plant_my_study.user.User;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
  Optional<RefreshToken> findById(UUID tokenId);
  List<RefreshToken> findAllByUser(User user);
}