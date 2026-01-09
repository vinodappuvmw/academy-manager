package com.academy.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseResponse(
    Long id,
    Long academyId,
    Long trainingCentreId,
    LocalDate date,
    BigDecimal amount,
    String category,
    String description,
    String payee,
    String paymentMethod,
    String referenceNumber) {}

