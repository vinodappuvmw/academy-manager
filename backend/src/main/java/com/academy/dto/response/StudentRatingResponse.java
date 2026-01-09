package com.academy.dto.response;

import java.time.LocalDate;
import java.util.Map;

public record StudentRatingResponse(
    Long id,
    Long academyId,
    Long studentId,
    Long sportId,
    Long trainingSessionId,
    Long ratingTemplateId,
    Long ratedByCoachId,
    LocalDate ratingDate,
    Map<String, Object> scores,
    String comments) {}

