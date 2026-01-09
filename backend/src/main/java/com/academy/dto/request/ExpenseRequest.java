package com.academy.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseRequest(
    Long trainingCentreId,
    @NotNull(message = "Date is required") LocalDate date,
    @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        BigDecimal amount,
    @NotBlank(message = "Category is required") String category,
    String description,
    String payee,
    String paymentMethod,
    String referenceNumber) {}

