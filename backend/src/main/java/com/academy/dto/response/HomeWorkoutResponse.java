package com.academy.dto.response;

import java.time.OffsetDateTime;
import java.util.List;

public record HomeWorkoutResponse(
    Long id,
    Long academyId,
    Long coachId,
    Long studentId,
    Long sportId,
    String title,
    String description,
    List<String> videoLinks,
    String imageUrl,
    String difficultyLevel,
    Integer durationMinutes,
    OffsetDateTime createdAt) {}

