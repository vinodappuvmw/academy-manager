package com.academy.exception;

import jakarta.validation.ConstraintViolationException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
      ResourceNotFoundException ex) {
    ErrorResponse error =
        new ErrorResponse(
            "Resource Not Found", ex.getMessage(), OffsetDateTime.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(BusinessRuleViolationException.class)
  public ResponseEntity<ErrorResponse> handleBusinessRuleViolationException(
      BusinessRuleViolationException ex) {
    ErrorResponse error =
        new ErrorResponse("Business Rule Violation", ex.getMessage(), OffsetDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(DuplicateResourceException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateResourceException(
      DuplicateResourceException ex) {
    ErrorResponse error =
        new ErrorResponse("Duplicate Resource", ex.getMessage(), OffsetDateTime.now());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    ErrorResponse error =
        new ErrorResponse(
            "Validation Failed", "Invalid input data", OffsetDateTime.now(), errors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ErrorResponse> handleConstraintViolationException(
      ConstraintViolationException ex) {
    ErrorResponse error =
        new ErrorResponse("Validation Failed", ex.getMessage(), OffsetDateTime.now());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
    ErrorResponse error =
        new ErrorResponse(
            "Internal Server Error",
            "An unexpected error occurred",
            OffsetDateTime.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  public record ErrorResponse(
      String error, String message, OffsetDateTime timestamp, Map<String, String> details) {
    public ErrorResponse(String error, String message, OffsetDateTime timestamp) {
      this(error, message, timestamp, null);
    }
  }
}

