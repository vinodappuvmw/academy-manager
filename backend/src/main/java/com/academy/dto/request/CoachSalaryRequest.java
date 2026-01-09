package com.academy.dto.request;

import com.academy.util.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CoachSalaryRequest(
    @NotNull(message = "Coach ID is required") Long coachId,
    @NotNull(message = "Year is required") Integer year,
    @NotNull(message = "Month is required")
        @Min(value = 1, message = "Month must be between 1 and 12")
        @Max(value = 12, message = "Month must be between 1 and 12")
        Integer month,
    @NotNull(message = "Base salary is required")
        @DecimalMin(value = "0.01", message = "Base salary must be greater than 0")
        BigDecimal baseSalary,
    @DecimalMin(value = "0.0", message = "Bonus amount cannot be negative")
        BigDecimal bonusAmount,
    @DecimalMin(value = "0.0", message = "Amount paid cannot be negative")
        BigDecimal amountPaid,
    LocalDate dueDate,
    LocalDate paidDate,
    @NotNull(message = "Status is required") PaymentStatus status,
    String notes) {}

