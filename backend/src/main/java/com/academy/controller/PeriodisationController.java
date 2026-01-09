package com.academy.controller;

import com.academy.dto.request.PeriodisationRequest;
import com.academy.dto.response.PeriodisationResponse;
import com.academy.service.PeriodisationService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/periodisations")
public class PeriodisationController {

  private final PeriodisationService periodisationService;

  public PeriodisationController(PeriodisationService periodisationService) {
    this.periodisationService = periodisationService;
  }

  @GetMapping
  public ResponseEntity<Page<PeriodisationResponse>> getAllPeriodisations(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(periodisationService.getAllPeriodisations(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PeriodisationResponse> getPeriodisationById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(periodisationService.getPeriodisationById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<PeriodisationResponse> createPeriodisation(
      @PathVariable Long academyId, @Valid @RequestBody PeriodisationRequest request) {
    PeriodisationResponse response =
        periodisationService.createPeriodisation(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PeriodisationResponse> updatePeriodisation(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody PeriodisationRequest request) {
    return ResponseEntity.ok(
        periodisationService.updatePeriodisation(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePeriodisation(
      @PathVariable Long academyId, @PathVariable Long id) {
    periodisationService.deletePeriodisation(academyId, id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/students/{studentId}")
  public ResponseEntity<Page<PeriodisationResponse>> getPeriodisationsByStudent(
      @PathVariable Long academyId, @PathVariable Long studentId, Pageable pageable) {
    return ResponseEntity.ok(
        periodisationService.getPeriodisationsByStudent(academyId, studentId, pageable));
  }

  @GetMapping("/training-programs/{trainingProgramId}")
  public ResponseEntity<Page<PeriodisationResponse>> getPeriodisationsByTrainingProgram(
      @PathVariable Long academyId,
      @PathVariable Long trainingProgramId,
      Pageable pageable) {
    return ResponseEntity.ok(
        periodisationService.getPeriodisationsByTrainingProgram(
            academyId, trainingProgramId, pageable));
  }
}

