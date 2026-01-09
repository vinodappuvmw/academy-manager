package com.academy.service;

import com.academy.dto.request.ExpenseRequest;
import com.academy.dto.response.ExpenseResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExpenseService {
  Page<ExpenseResponse> getAllExpenses(Long academyId, Pageable pageable);

  ExpenseResponse getExpenseById(Long academyId, Long id);

  ExpenseResponse createExpense(Long academyId, ExpenseRequest request);

  ExpenseResponse updateExpense(Long academyId, Long id, ExpenseRequest request);

  void deleteExpense(Long academyId, Long id);
}

