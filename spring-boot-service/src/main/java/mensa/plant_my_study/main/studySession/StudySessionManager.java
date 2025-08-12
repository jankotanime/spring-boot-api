package mensa.plant_my_study.main.studySession;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class StudySessionManager {
  private final StudySessionRepository studySessionRepository;

  public void addStudySessionToDataBase(StudySession studySession) {
    studySessionRepository.save(studySession);
  }
}
