package mensa.plant_my_study.main.studySession;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mensa.plant_my_study.main.user.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudySessionService {
  private final StudySessionRepository studySessionRepository;
  private final StudySessionManager studySessionManager;

  public Map<String, String> postSession(User user, Integer timeMin, Instant sessionEndAt) {
    Map<String, String> result = new HashMap<>();

    List<StudySession> sessions = studySessionRepository.findAllByUser(user);

    if (timeMin > 1440 || Duration.between(user.getCreatedAt(),
    sessionEndAt).toMinutes() < timeMin) {
      result.put("err", "Invalid session time");
      return result;
    }

    if (sessions.isEmpty()) {
      StudySession studySession = new StudySession(user, timeMin, sessionEndAt);
      studySessionManager.addStudySessionToDataBase(studySession);
      result.put("data", "Session authenticated");
      return result;
    }

    StudySession latestSession = sessions.stream()
      .max((s1, s2) -> s1.getSessionEndAt().compareTo(s2.getSessionEndAt()))
      .orElse(null);

    if (Duration.between(sessionEndAt, latestSession.getSessionEndAt()).toMinutes()
    >= timeMin) {
      StudySession studySession = new StudySession(user, timeMin, sessionEndAt);
      studySessionManager.addStudySessionToDataBase(studySession);
      result.put("data", "Session authenticated");
      return result;
    }

    result.put("err", "Invalid session time");
    return result;
  }
}
