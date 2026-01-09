package com.academy.dto.response;

import java.time.LocalDate;
import java.util.List;

public record StudentResponse(
    Long id,
    Long academyId,
    String name,
    LocalDate dateOfBirth,
    String address,
    String phone,
    String email,
    String emergencyContact,
    String position,
    String highestEducation,
    String gender,
    String photoUrl,
    List<Long> sportIds) {}

