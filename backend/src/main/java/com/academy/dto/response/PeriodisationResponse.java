package com.academy.dto.response;

import java.time.OffsetDateTime;

public record PeriodisationResponse(
    Long id,
    Long academyId,
    Long studentId,
    Long trainingProgramId,
    String weeklyPlan,
    String monthlyPlan,
    String yearlyPlan,
    String weeklyPlanImageUrl,
    String monthlyPlanImageUrl,
    String yearlyPlanImageUrl,
    Long createdByCoachId,
    OffsetDateTime createdAt,
    OffsetDateTime updatedAt) {}

