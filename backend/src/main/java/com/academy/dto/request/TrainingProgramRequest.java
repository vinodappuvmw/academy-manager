package com.academy.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TrainingProgramRequest(
    @NotBlank(message = "Name is required") String name, String description) {}

