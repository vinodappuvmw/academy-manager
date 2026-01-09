package com.academy.controller;

import com.academy.dto.request.StudentFeeRequest;
import com.academy.dto.response.StudentFeeResponse;
import com.academy.service.StudentFeeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/student-fees")
public class StudentFeeController {

  private final StudentFeeService studentFeeService;

  public StudentFeeController(StudentFeeService studentFeeService) {
    this.studentFeeService = studentFeeService;
  }

  @GetMapping
  public ResponseEntity<Page<StudentFeeResponse>> getAllStudentFees(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(studentFeeService.getAllStudentFees(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentFeeResponse> getStudentFeeById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(studentFeeService.getStudentFeeById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<StudentFeeResponse> createStudentFee(
      @PathVariable Long academyId, @Valid @RequestBody StudentFeeRequest request) {
    StudentFeeResponse response = studentFeeService.createStudentFee(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentFeeResponse> updateStudentFee(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody StudentFeeRequest request) {
    return ResponseEntity.ok(studentFeeService.updateStudentFee(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudentFee(
      @PathVariable Long academyId, @PathVariable Long id) {
    studentFeeService.deleteStudentFee(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

