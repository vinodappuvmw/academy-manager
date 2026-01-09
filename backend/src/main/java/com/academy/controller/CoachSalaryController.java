package com.academy.controller;

import com.academy.dto.request.CoachSalaryRequest;
import com.academy.dto.response.CoachSalaryResponse;
import com.academy.service.CoachSalaryService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/coach-salaries")
public class CoachSalaryController {

  private final CoachSalaryService coachSalaryService;

  public CoachSalaryController(CoachSalaryService coachSalaryService) {
    this.coachSalaryService = coachSalaryService;
  }

  @GetMapping
  public ResponseEntity<Page<CoachSalaryResponse>> getAllCoachSalaries(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(coachSalaryService.getAllCoachSalaries(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CoachSalaryResponse> getCoachSalaryById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(coachSalaryService.getCoachSalaryById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<CoachSalaryResponse> createCoachSalary(
      @PathVariable Long academyId, @Valid @RequestBody CoachSalaryRequest request) {
    CoachSalaryResponse response = coachSalaryService.createCoachSalary(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CoachSalaryResponse> updateCoachSalary(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody CoachSalaryRequest request) {
    return ResponseEntity.ok(coachSalaryService.updateCoachSalary(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCoachSalary(
      @PathVariable Long academyId, @PathVariable Long id) {
    coachSalaryService.deleteCoachSalary(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

