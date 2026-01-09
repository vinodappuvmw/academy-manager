package com.academy.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record HomeWorkoutRequest(
    @NotNull(message = "Coach ID is required") Long coachId,
    @NotNull(message = "Student ID is required") Long studentId,
    @NotNull(message = "Sport ID is required") Long sportId,
    @NotBlank(message = "Title is required") String title,
    String description,
    List<String> videoLinks,
    String imageUrl,
    String difficultyLevel,
    @Min(value = 1, message = "Duration must be at least 1 minute") Integer durationMinutes) {}

