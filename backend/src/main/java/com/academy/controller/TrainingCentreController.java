package com.academy.controller;

import com.academy.dto.request.TrainingCentreRequest;
import com.academy.dto.response.TrainingCentreResponse;
import com.academy.service.TrainingCentreService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/training-centres")
public class TrainingCentreController {

  private final TrainingCentreService trainingCentreService;

  public TrainingCentreController(TrainingCentreService trainingCentreService) {
    this.trainingCentreService = trainingCentreService;
  }

  @GetMapping
  public ResponseEntity<Page<TrainingCentreResponse>> getAllTrainingCentres(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(
        trainingCentreService.getAllTrainingCentres(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrainingCentreResponse> getTrainingCentreById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(
        trainingCentreService.getTrainingCentreById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<TrainingCentreResponse> createTrainingCentre(
      @PathVariable Long academyId, @Valid @RequestBody TrainingCentreRequest request) {
    TrainingCentreResponse response =
        trainingCentreService.createTrainingCentre(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TrainingCentreResponse> updateTrainingCentre(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody TrainingCentreRequest request) {
    return ResponseEntity.ok(
        trainingCentreService.updateTrainingCentre(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTrainingCentre(
      @PathVariable Long academyId, @PathVariable Long id) {
    trainingCentreService.deleteTrainingCentre(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

