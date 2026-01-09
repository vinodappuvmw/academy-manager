package com.academy.dto.request;

import com.academy.util.AttendanceStatus;
import jakarta.validation.constraints.NotNull;
import java.util.Map;

public record StudentAttendanceRequest(
    @NotNull(message = "Training session ID is required") Long trainingSessionId,
    @NotNull(message = "Student ID is required") Long studentId,
    @NotNull(message = "Status is required") AttendanceStatus status,
    Long markedByCoachId,
    String notes) {}

