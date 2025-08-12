package mensa.plant_my_study.main.studySession;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mensa.plant_my_study.main.user.User;

public interface StudySessionRepository extends JpaRepository<StudySession, Long> {
  List<StudySession> findAllByUser(User user);
}
