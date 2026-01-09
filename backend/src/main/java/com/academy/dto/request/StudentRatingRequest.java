package com.academy.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Map;

public record StudentRatingRequest(
    @NotNull(message = "Student ID is required") Long studentId,
    @NotNull(message = "Sport ID is required") Long sportId,
    Long trainingSessionId,
    @NotNull(message = "Rating template ID is required") Long ratingTemplateId,
    Long ratedByCoachId,
    @NotNull(message = "Rating date is required") LocalDate ratingDate,
    @NotNull(message = "Scores are required") Map<String, Object> scores,
    String comments) {}

