package com.academy.controller;

import com.academy.dto.request.TrainingProgramRequest;
import com.academy.dto.response.TrainingProgramResponse;
import com.academy.service.TrainingProgramService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/training-programs")
public class TrainingProgramController {

  private final TrainingProgramService trainingProgramService;

  public TrainingProgramController(TrainingProgramService trainingProgramService) {
    this.trainingProgramService = trainingProgramService;
  }

  @GetMapping
  public ResponseEntity<Page<TrainingProgramResponse>> getAllTrainingPrograms(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(
        trainingProgramService.getAllTrainingPrograms(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrainingProgramResponse> getTrainingProgramById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(trainingProgramService.getTrainingProgramById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<TrainingProgramResponse> createTrainingProgram(
      @PathVariable Long academyId, @Valid @RequestBody TrainingProgramRequest request) {
    TrainingProgramResponse response =
        trainingProgramService.createTrainingProgram(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TrainingProgramResponse> updateTrainingProgram(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody TrainingProgramRequest request) {
    return ResponseEntity.ok(
        trainingProgramService.updateTrainingProgram(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTrainingProgram(
      @PathVariable Long academyId, @PathVariable Long id) {
    trainingProgramService.deleteTrainingProgram(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

