package com.academy.dto.request;

public record PeriodisationRequest(
    Long studentId,
    Long trainingProgramId,
    Long createdByCoachId,
    String weeklyPlan,
    String monthlyPlan,
    String yearlyPlan,
    String weeklyPlanImageUrl,
    String monthlyPlanImageUrl,
    String yearlyPlanImageUrl) {}

