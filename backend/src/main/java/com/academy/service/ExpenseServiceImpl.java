package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.Expense;
import com.academy.domain.TrainingCentre;
import com.academy.dto.request.ExpenseRequest;
import com.academy.dto.response.ExpenseResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.ExpenseMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.ExpenseRepository;
import com.academy.repository.TrainingCentreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpenseServiceImpl implements ExpenseService {

  private final ExpenseRepository expenseRepository;
  private final AcademyRepository academyRepository;
  private final TrainingCentreRepository trainingCentreRepository;
  private final ExpenseMapper expenseMapper;

  public ExpenseServiceImpl(
      ExpenseRepository expenseRepository,
      AcademyRepository academyRepository,
      TrainingCentreRepository trainingCentreRepository,
      ExpenseMapper expenseMapper) {
    this.expenseRepository = expenseRepository;
    this.academyRepository = academyRepository;
    this.trainingCentreRepository = trainingCentreRepository;
    this.expenseMapper = expenseMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ExpenseResponse> getAllExpenses(Long academyId, Pageable pageable) {
    return expenseRepository.findByAcademyId(academyId, pageable).map(expenseMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public ExpenseResponse getExpenseById(Long academyId, Long id) {
    Expense expense =
        expenseRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    return expenseMapper.toResponse(expense);
  }

  @Override
  @Transactional
  public ExpenseResponse createExpense(Long academyId, ExpenseRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    Expense expense = expenseMapper.toEntity(request);
    expense.setAcademy(academy);

    if (request.trainingCentreId() != null) {
      TrainingCentre trainingCentre =
          trainingCentreRepository
              .findByAcademyIdAndId(academyId, request.trainingCentreId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training centre not found with id: " + request.trainingCentreId()));
      expense.setTrainingCentre(trainingCentre);
    }

    Expense saved = expenseRepository.save(expense);
    return expenseMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public ExpenseResponse updateExpense(Long academyId, Long id, ExpenseRequest request) {
    Expense expense =
        expenseRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

    expense.setDate(request.date());
    expense.setAmount(request.amount());
    expense.setCategory(request.category());
    expense.setDescription(request.description());
    expense.setPayee(request.payee());
    expense.setPaymentMethod(request.paymentMethod());
    expense.setReferenceNumber(request.referenceNumber());

    if (request.trainingCentreId() != null) {
      TrainingCentre trainingCentre =
          trainingCentreRepository
              .findByAcademyIdAndId(academyId, request.trainingCentreId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training centre not found with id: " + request.trainingCentreId()));
      expense.setTrainingCentre(trainingCentre);
    } else {
      expense.setTrainingCentre(null);
    }

    Expense updated = expenseRepository.save(expense);
    return expenseMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteExpense(Long academyId, Long id) {
    Expense expense =
        expenseRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
    expenseRepository.delete(expense);
  }
}

