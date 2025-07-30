package mensa.plant_my_study.main.plant;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlantService {
  private final PlantRepository plantRepository;

  public List<Plant> getAllPlants() {
    return plantRepository.findAll();
  }
}
