package com.academy.dto.response;

import com.academy.util.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentFeeResponse(
    Long id,
    Long academyId,
    Long studentId,
    Integer year,
    Integer month,
    BigDecimal amountDue,
    BigDecimal amountPaid,
    LocalDate dueDate,
    LocalDate paidDate,
    PaymentStatus status,
    String notes) {}

