package com.academy.controller;

import com.academy.dto.request.AcademyRequest;
import com.academy.dto.response.AcademyResponse;
import com.academy.service.AcademyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies")
public class AcademyController {

  private final AcademyService academyService;

  public AcademyController(AcademyService academyService) {
    this.academyService = academyService;
  }

  @GetMapping
  public ResponseEntity<Page<AcademyResponse>> getAllAcademies(Pageable pageable) {
    return ResponseEntity.ok(academyService.getAllAcademies(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<AcademyResponse> getAcademyById(@PathVariable Long id) {
    return ResponseEntity.ok(academyService.getAcademyById(id));
  }

  @PostMapping
  public ResponseEntity<AcademyResponse> createAcademy(@Valid @RequestBody AcademyRequest request) {
    AcademyResponse response = academyService.createAcademy(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<AcademyResponse> updateAcademy(
      @PathVariable Long id, @Valid @RequestBody AcademyRequest request) {
    return ResponseEntity.ok(academyService.updateAcademy(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAcademy(@PathVariable Long id) {
    academyService.deleteAcademy(id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{academyId}/sports/{sportId}")
  public ResponseEntity<Void> addSportToAcademy(
      @PathVariable Long academyId, @PathVariable Long sportId) {
    academyService.addSportToAcademy(academyId, sportId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{academyId}/sports/{sportId}")
  public ResponseEntity<Void> removeSportFromAcademy(
      @PathVariable Long academyId, @PathVariable Long sportId) {
    academyService.removeSportFromAcademy(academyId, sportId);
    return ResponseEntity.noContent().build();
  }
}

