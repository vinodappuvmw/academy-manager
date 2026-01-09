package com.academy.controller;

import com.academy.dto.request.StudentRequest;
import com.academy.dto.response.StudentResponse;
import com.academy.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/students")
public class StudentController {

  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  public ResponseEntity<Page<StudentResponse>> getAllStudents(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(studentService.getAllStudents(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<StudentResponse> getStudentById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(studentService.getStudentById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<StudentResponse> createStudent(
      @PathVariable Long academyId, @Valid @RequestBody StudentRequest request) {
    StudentResponse response = studentService.createStudent(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<StudentResponse> updateStudent(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody StudentRequest request) {
    return ResponseEntity.ok(studentService.updateStudent(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable Long academyId, @PathVariable Long id) {
    studentService.deleteStudent(academyId, id);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/{studentId}/sports/{sportId}")
  public ResponseEntity<Void> enrollStudentInSport(
      @PathVariable Long academyId,
      @PathVariable Long studentId,
      @PathVariable Long sportId) {
    studentService.enrollStudentInSport(academyId, studentId, sportId);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{studentId}/sports/{sportId}")
  public ResponseEntity<Void> unenrollStudentFromSport(
      @PathVariable Long academyId,
      @PathVariable Long studentId,
      @PathVariable Long sportId) {
    studentService.unenrollStudentFromSport(academyId, studentId, sportId);
    return ResponseEntity.noContent().build();
  }
}

