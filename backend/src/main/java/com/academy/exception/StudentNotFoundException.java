package com.academy.exception;

public class StudentNotFoundException extends ResourceNotFoundException {
  public StudentNotFoundException(Long academyId, Long id) {
    super("Student not found with id: " + id + " in academy: " + academyId);
  }
}

