package com.academy.dto.response;

import java.time.LocalDate;
import java.util.List;

public record CoachResponse(
    Long id,
    Long academyId,
    String name,
    String address,
    String phone,
    String email,
    String sportSubject,
    String gender,
    Integer yearsExperience,
    String qualifications,
    String specialization,
    LocalDate dateOfBirth,
    String photoUrl,
    List<Long> sportIds) {}

