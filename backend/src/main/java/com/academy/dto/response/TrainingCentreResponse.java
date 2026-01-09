package com.academy.dto.response;

public record TrainingCentreResponse(
    Long id,
    Long academyId,
    String name,
    String googleMapsLink,
    String address,
    String pinCode,
    String state,
    String country,
    String registrationNumber,
    String affiliationBody,
    Integer establishedYear,
    String city,
    String email,
    String website,
    Integer numberOfPitches,
    String pitchType,
    String pitchDimensions,
    Boolean lightingAvailable,
    Boolean gymAvailable,
    Boolean physioRoom,
    Boolean changingRooms,
    Boolean hostelFacility) {}

