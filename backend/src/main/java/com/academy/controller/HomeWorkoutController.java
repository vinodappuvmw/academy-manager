package com.academy.controller;

import com.academy.dto.request.HomeWorkoutRequest;
import com.academy.dto.response.HomeWorkoutResponse;
import com.academy.service.HomeWorkoutService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/home-workouts")
public class HomeWorkoutController {

  private final HomeWorkoutService homeWorkoutService;

  public HomeWorkoutController(HomeWorkoutService homeWorkoutService) {
    this.homeWorkoutService = homeWorkoutService;
  }

  @GetMapping
  public ResponseEntity<Page<HomeWorkoutResponse>> getAllHomeWorkouts(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(homeWorkoutService.getAllHomeWorkouts(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<HomeWorkoutResponse> getHomeWorkoutById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(homeWorkoutService.getHomeWorkoutById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<HomeWorkoutResponse> createHomeWorkout(
      @PathVariable Long academyId, @Valid @RequestBody HomeWorkoutRequest request) {
    HomeWorkoutResponse response = homeWorkoutService.createHomeWorkout(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<HomeWorkoutResponse> updateHomeWorkout(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody HomeWorkoutRequest request) {
    return ResponseEntity.ok(homeWorkoutService.updateHomeWorkout(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteHomeWorkout(
      @PathVariable Long academyId, @PathVariable Long id) {
    homeWorkoutService.deleteHomeWorkout(academyId, id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/students/{studentId}")
  public ResponseEntity<Page<HomeWorkoutResponse>> getHomeWorkoutsByStudent(
      @PathVariable Long academyId, @PathVariable Long studentId, Pageable pageable) {
    return ResponseEntity.ok(
        homeWorkoutService.getHomeWorkoutsByStudent(academyId, studentId, pageable));
  }

  @GetMapping("/coaches/{coachId}")
  public ResponseEntity<Page<HomeWorkoutResponse>> getHomeWorkoutsByCoach(
      @PathVariable Long academyId, @PathVariable Long coachId, Pageable pageable) {
    return ResponseEntity.ok(homeWorkoutService.getHomeWorkoutsByCoach(academyId, coachId, pageable));
  }

  @GetMapping("/sports/{sportId}")
  public ResponseEntity<Page<HomeWorkoutResponse>> getHomeWorkoutsBySport(
      @PathVariable Long academyId, @PathVariable Long sportId, Pageable pageable) {
    return ResponseEntity.ok(homeWorkoutService.getHomeWorkoutsBySport(academyId, sportId, pageable));
  }

  @GetMapping("/students/{studentId}/sports/{sportId}")
  public ResponseEntity<Page<HomeWorkoutResponse>> getHomeWorkoutsByStudentAndSport(
      @PathVariable Long academyId,
      @PathVariable Long studentId,
      @PathVariable Long sportId,
      Pageable pageable) {
    return ResponseEntity.ok(
        homeWorkoutService.getHomeWorkoutsByStudentAndSport(academyId, studentId, sportId, pageable));
  }
}

