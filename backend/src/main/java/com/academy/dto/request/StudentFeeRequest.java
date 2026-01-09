package com.academy.dto.request;

import com.academy.util.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record StudentFeeRequest(
    @NotNull(message = "Student ID is required") Long studentId,
    @NotNull(message = "Year is required") Integer year,
    @NotNull(message = "Month is required")
        @Min(value = 1, message = "Month must be between 1 and 12")
        @Max(value = 12, message = "Month must be between 1 and 12")
        Integer month,
    @NotNull(message = "Amount due is required")
        @DecimalMin(value = "0.01", message = "Amount due must be greater than 0")
        BigDecimal amountDue,
    @DecimalMin(value = "0.0", message = "Amount paid cannot be negative")
        BigDecimal amountPaid,
    LocalDate dueDate,
    LocalDate paidDate,
    @NotNull(message = "Status is required") PaymentStatus status,
    String notes) {}

