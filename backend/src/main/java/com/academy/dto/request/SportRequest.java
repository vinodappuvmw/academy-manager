package com.academy.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SportRequest(
    @NotBlank(message = "Name is required") String name,
    String description,
    String category) {}

