package com.academy.dto.response;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record TrainingSessionResponse(
    Long id,
    Long academyId,
    LocalDate date,
    LocalTime startTime,
    Integer durationMinutes,
    Long trainingCentreId,
    Long programId,
    Long planId,
    Long sessionOwnerId,
    Long sportId,
    List<Long> coachIds,
    List<Long> studentIds,
    String notes) {}

