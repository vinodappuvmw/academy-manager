package com.academy.controller;

import com.academy.dto.request.SportRequest;
import com.academy.dto.response.SportResponse;
import com.academy.service.SportService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sports")
public class SportController {

  private final SportService sportService;

  public SportController(SportService sportService) {
    this.sportService = sportService;
  }

  @GetMapping
  public ResponseEntity<Page<SportResponse>> getAllSports(Pageable pageable) {
    return ResponseEntity.ok(sportService.getAllSports(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<SportResponse> getSportById(@PathVariable Long id) {
    return ResponseEntity.ok(sportService.getSportById(id));
  }

  @PostMapping
  public ResponseEntity<SportResponse> createSport(@Valid @RequestBody SportRequest request) {
    SportResponse response = sportService.createSport(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SportResponse> updateSport(
      @PathVariable Long id, @Valid @RequestBody SportRequest request) {
    return ResponseEntity.ok(sportService.updateSport(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
    sportService.deleteSport(id);
    return ResponseEntity.noContent().build();
  }
}

