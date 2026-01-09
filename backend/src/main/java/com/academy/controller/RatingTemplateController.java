package com.academy.controller;

import com.academy.dto.request.RatingTemplateRequest;
import com.academy.dto.response.RatingTemplateResponse;
import com.academy.service.RatingTemplateService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/rating-templates")
public class RatingTemplateController {

  private final RatingTemplateService ratingTemplateService;

  public RatingTemplateController(RatingTemplateService ratingTemplateService) {
    this.ratingTemplateService = ratingTemplateService;
  }

  @GetMapping
  public ResponseEntity<Page<RatingTemplateResponse>> getAllRatingTemplates(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(
        ratingTemplateService.getAllRatingTemplates(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<RatingTemplateResponse> getRatingTemplateById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(ratingTemplateService.getRatingTemplateById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<RatingTemplateResponse> createRatingTemplate(
      @PathVariable Long academyId, @Valid @RequestBody RatingTemplateRequest request) {
    RatingTemplateResponse response =
        ratingTemplateService.createRatingTemplate(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<RatingTemplateResponse> updateRatingTemplate(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody RatingTemplateRequest request) {
    return ResponseEntity.ok(
        ratingTemplateService.updateRatingTemplate(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteRatingTemplate(
      @PathVariable Long academyId, @PathVariable Long id) {
    ratingTemplateService.deleteRatingTemplate(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

