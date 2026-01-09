package com.academy.dto.response;

import java.util.List;

public record AcademyResponse(
    Long id,
    String name,
    String address,
    String phone,
    String email,
    String website,
    String logoUrl,
    List<Long> sportIds) {}

