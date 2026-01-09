package com.academy.exception;

public class CoachNotFoundException extends ResourceNotFoundException {
  public CoachNotFoundException(Long academyId, Long id) {
    super("Coach not found with id: " + id + " in academy: " + academyId);
  }
}

