package com.academy.controller;

import com.academy.dto.request.TrainingSessionRequest;
import com.academy.dto.response.TrainingSessionResponse;
import com.academy.service.TrainingSessionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/training-sessions")
public class TrainingSessionController {

  private final TrainingSessionService trainingSessionService;

  public TrainingSessionController(TrainingSessionService trainingSessionService) {
    this.trainingSessionService = trainingSessionService;
  }

  @GetMapping
  public ResponseEntity<Page<TrainingSessionResponse>> getAllTrainingSessions(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(
        trainingSessionService.getAllTrainingSessions(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TrainingSessionResponse> getTrainingSessionById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(trainingSessionService.getTrainingSessionById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<TrainingSessionResponse> createTrainingSession(
      @PathVariable Long academyId, @Valid @RequestBody TrainingSessionRequest request) {
    TrainingSessionResponse response =
        trainingSessionService.createTrainingSession(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TrainingSessionResponse> updateTrainingSession(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody TrainingSessionRequest request) {
    return ResponseEntity.ok(
        trainingSessionService.updateTrainingSession(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTrainingSession(
      @PathVariable Long academyId, @PathVariable Long id) {
    trainingSessionService.deleteTrainingSession(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

