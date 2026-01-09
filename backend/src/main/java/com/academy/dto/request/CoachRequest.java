package com.academy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record CoachRequest(
    @NotBlank(message = "Name is required") String name,
    String address,
    String phone,
    @Email(message = "Invalid email format") String email,
    String sportSubject,
    String gender,
    Integer yearsExperience,
    String qualifications,
    String specialization,
    @Past(message = "Date of birth must be in the past") LocalDate dateOfBirth,
    String photoUrl) {}

