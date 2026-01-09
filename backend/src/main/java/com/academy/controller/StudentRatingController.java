package com.academy.controller;

import com.academy.dto.request.StudentRatingRequest;
import com.academy.dto.response.StudentRatingResponse;
import com.academy.service.StudentRatingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/student-ratings")
public class StudentRatingController {

  private final StudentRatingService studentRatingService;

  public StudentRatingController(StudentRatingService studentRatingService) {
    this.studentRatingService = studentRatingService;
  }

  @GetMapping
  public ResponseEntity<Page<StudentRatingResponse>> getAllStudentRatings(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(studentRatingService.getAllStudentRatings(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentRatingResponse> getStudentRatingById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(studentRatingService.getStudentRatingById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<StudentRatingResponse> createStudentRating(
      @PathVariable Long academyId, @Valid @RequestBody StudentRatingRequest request) {
    StudentRatingResponse response =
        studentRatingService.createStudentRating(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentRatingResponse> updateStudentRating(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody StudentRatingRequest request) {
    return ResponseEntity.ok(studentRatingService.updateStudentRating(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudentRating(
      @PathVariable Long academyId, @PathVariable Long id) {
    studentRatingService.deleteStudentRating(academyId, id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/students/{studentId}/sports/{sportId}/history")
  public ResponseEntity<Page<StudentRatingResponse>> getStudentRatingHistory(
      @PathVariable Long academyId,
      @PathVariable Long studentId,
      @PathVariable Long sportId,
      Pageable pageable) {
    return ResponseEntity.ok(
        studentRatingService.getStudentRatingHistory(academyId, studentId, sportId, pageable));
  }
}

