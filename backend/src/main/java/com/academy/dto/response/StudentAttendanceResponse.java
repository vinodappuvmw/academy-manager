package com.academy.dto.response;

import com.academy.util.AttendanceStatus;
import java.time.OffsetDateTime;

public record StudentAttendanceResponse(
    Long id,
    Long academyId,
    Long trainingSessionId,
    Long studentId,
    AttendanceStatus status,
    Long markedByCoachId,
    OffsetDateTime markedAt,
    String notes) {}

