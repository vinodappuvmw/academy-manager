package com.academy.controller;

import com.academy.dto.request.StudentAttendanceRequest;
import com.academy.dto.response.StudentAttendanceResponse;
import com.academy.service.StudentAttendanceService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/student-attendances")
public class StudentAttendanceController {

  private final StudentAttendanceService studentAttendanceService;

  public StudentAttendanceController(StudentAttendanceService studentAttendanceService) {
    this.studentAttendanceService = studentAttendanceService;
  }

  @GetMapping
  public ResponseEntity<Page<StudentAttendanceResponse>> getAllStudentAttendances(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(
        studentAttendanceService.getAllStudentAttendances(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentAttendanceResponse> getStudentAttendanceById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(
        studentAttendanceService.getStudentAttendanceById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<StudentAttendanceResponse> createStudentAttendance(
      @PathVariable Long academyId, @Valid @RequestBody StudentAttendanceRequest request) {
    StudentAttendanceResponse response =
        studentAttendanceService.createStudentAttendance(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentAttendanceResponse> updateStudentAttendance(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody StudentAttendanceRequest request) {
    return ResponseEntity.ok(
        studentAttendanceService.updateStudentAttendance(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudentAttendance(
      @PathVariable Long academyId, @PathVariable Long id) {
    studentAttendanceService.deleteStudentAttendance(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

