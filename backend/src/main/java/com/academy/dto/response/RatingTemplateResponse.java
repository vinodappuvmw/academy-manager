package com.academy.dto.response;

import java.util.Map;

public record RatingTemplateResponse(
    Long id, Long academyId, String name, Boolean isActive, Map<String, Object> schema) {}

