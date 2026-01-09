package com.academy.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PlanRequest(
    @NotBlank(message = "Name is required") String name,
    String description,
    Long coachId,
    String imageUrl) {}

