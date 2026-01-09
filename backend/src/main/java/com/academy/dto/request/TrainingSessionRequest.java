package com.academy.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record TrainingSessionRequest(
    @NotNull(message = "Date is required") LocalDate date,
    @NotNull(message = "Start time is required") LocalTime startTime,
    @NotNull(message = "Duration is required")
        @Min(value = 1, message = "Duration must be at least 1 minute")
        Integer durationMinutes,
    Long trainingCentreId,
    Long programId,
    Long planId,
    Long sessionOwnerId,
    Long sportId,
    List<Long> coachIds,
    List<Long> studentIds,
    String notes) {}

