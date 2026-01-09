package com.academy.controller;

import com.academy.dto.request.PlanRequest;
import com.academy.dto.response.PlanResponse;
import com.academy.service.PlanService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/plans")
public class PlanController {

  private final PlanService planService;

  public PlanController(PlanService planService) {
    this.planService = planService;
  }

  @GetMapping
  public ResponseEntity<Page<PlanResponse>> getAllPlans(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(planService.getAllPlans(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<PlanResponse> getPlanById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(planService.getPlanById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<PlanResponse> createPlan(
      @PathVariable Long academyId, @Valid @RequestBody PlanRequest request) {
    PlanResponse response = planService.createPlan(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PlanResponse> updatePlan(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody PlanRequest request) {
    return ResponseEntity.ok(planService.updatePlan(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePlan(@PathVariable Long academyId, @PathVariable Long id) {
    planService.deletePlan(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

