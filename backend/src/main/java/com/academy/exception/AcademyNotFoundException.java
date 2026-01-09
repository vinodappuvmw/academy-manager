package com.academy.exception;

public class AcademyNotFoundException extends ResourceNotFoundException {
  public AcademyNotFoundException(Long id) {
    super("Academy not found with id: " + id);
  }
}

