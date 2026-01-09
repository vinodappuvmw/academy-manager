package com.academy.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record TrainingCentreRequest(
    @NotBlank(message = "Name is required") String name,
    String googleMapsLink,
    String address,
    String pinCode,
    String state,
    String country,
    String registrationNumber,
    String affiliationBody,
    Integer establishedYear,
    String city,
    @Email(message = "Invalid email format") String email,
    String website,
    Integer numberOfPitches,
    String pitchType,
    String pitchDimensions,
    Boolean lightingAvailable,
    Boolean gymAvailable,
    Boolean physioRoom,
    Boolean changingRooms,
    Boolean hostelFacility) {}

