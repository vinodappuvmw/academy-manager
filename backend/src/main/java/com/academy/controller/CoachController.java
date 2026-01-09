package com.academy.controller;

import com.academy.dto.request.CoachRequest;
import com.academy.dto.response.CoachResponse;
import com.academy.service.CoachService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/coaches")
public class CoachController {

  private final CoachService coachService;

  public CoachController(CoachService coachService) {
    this.coachService = coachService;
  }

  @GetMapping
  public ResponseEntity<Page<CoachResponse>> getAllCoaches(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(coachService.getAllCoaches(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CoachResponse> getCoachById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(coachService.getCoachById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<CoachResponse> createCoach(
      @PathVariable Long academyId, @Valid @RequestBody CoachRequest request) {
    CoachResponse response = coachService.createCoach(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CoachResponse> updateCoach(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody CoachRequest request) {
    return ResponseEntity.ok(coachService.updateCoach(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCoach(@PathVariable Long academyId, @PathVariable Long id) {
    coachService.deleteCoach(academyId, id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{coachId}/sports/{sportId}")
  public ResponseEntity<Void> addSportToCoach(
      @PathVariable Long academyId,
      @PathVariable Long coachId,
      @PathVariable Long sportId) {
    coachService.addSportToCoach(academyId, coachId, sportId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{coachId}/sports/{sportId}")
  public ResponseEntity<Void> removeSportFromCoach(
      @PathVariable Long academyId,
      @PathVariable Long coachId,
      @PathVariable Long sportId) {
    coachService.removeSportFromCoach(academyId, coachId, sportId);
    return ResponseEntity.noContent().build();
  }
}

