package com.academy.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record RatingTemplateRequest(
    @NotBlank(message = "Name is required") String name,
    Boolean isActive,
    @NotNull(message = "Schema is required") Map<String, Object> schema) {}

