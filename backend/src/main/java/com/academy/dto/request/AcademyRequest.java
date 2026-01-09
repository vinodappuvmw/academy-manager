package com.academy.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AcademyRequest(
    @NotBlank(message = "Name is required") String name,
    String address,
    String phone,
    String email,
    String website,
    String logoUrl) {}

