package com.academy.dto.response;

import com.academy.util.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CoachSalaryResponse(
    Long id,
    Long academyId,
    Long coachId,
    Integer year,
    Integer month,
    BigDecimal baseSalary,
    BigDecimal bonusAmount,
    BigDecimal amountPaid,
    LocalDate dueDate,
    LocalDate paidDate,
    PaymentStatus status,
    String notes) {}

