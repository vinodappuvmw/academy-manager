package com.academy.dto.response;

public record PlanResponse(
    Long id, Long academyId, Long coachId, String name, String description, String imageUrl) {}

