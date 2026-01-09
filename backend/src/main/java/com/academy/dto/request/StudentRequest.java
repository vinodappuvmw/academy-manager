package com.academy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record StudentRequest(
    @NotBlank(message = "Name is required") String name,
    @Past(message = "Date of birth must be in the past") LocalDate dateOfBirth,
    String address,
    String phone,
    @Email(message = "Invalid email format") String email,
    String emergencyContact,
    String position,
    String highestEducation,
    String gender,
    String photoUrl) {}

