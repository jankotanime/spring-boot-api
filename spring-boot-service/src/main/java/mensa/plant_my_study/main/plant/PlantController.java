package mensa.plant_my_study.main.plant;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/plant")
public class PlantController {
  private final PlantService plantService;

  @GetMapping("/")
  public ResponseEntity<List<Plant>> getAllUsers(){
    return ResponseEntity.ok(plantService.getAllPlants());
  }
}
