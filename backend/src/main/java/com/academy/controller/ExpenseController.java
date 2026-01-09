package com.academy.controller;

import com.academy.dto.request.ExpenseRequest;
import com.academy.dto.response.ExpenseResponse;
import com.academy.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/academies/{academyId}/expenses")
public class ExpenseController {

  private final ExpenseService expenseService;

  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @GetMapping
  public ResponseEntity<Page<ExpenseResponse>> getAllExpenses(
      @PathVariable Long academyId, Pageable pageable) {
    return ResponseEntity.ok(expenseService.getAllExpenses(academyId, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ExpenseResponse> getExpenseById(
      @PathVariable Long academyId, @PathVariable Long id) {
    return ResponseEntity.ok(expenseService.getExpenseById(academyId, id));
  }

  @PostMapping
  public ResponseEntity<ExpenseResponse> createExpense(
      @PathVariable Long academyId, @Valid @RequestBody ExpenseRequest request) {
    ExpenseResponse response = expenseService.createExpense(academyId, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ExpenseResponse> updateExpense(
      @PathVariable Long academyId,
      @PathVariable Long id,
      @Valid @RequestBody ExpenseRequest request) {
    return ResponseEntity.ok(expenseService.updateExpense(academyId, id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteExpense(@PathVariable Long academyId, @PathVariable Long id) {
    expenseService.deleteExpense(academyId, id);
    return ResponseEntity.noContent().build();
  }
}

