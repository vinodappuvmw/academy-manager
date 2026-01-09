package com.academy.mapper;

import com.academy.domain.Academy;
import com.academy.dto.request.AcademyRequest;
import com.academy.dto.response.AcademyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import org.mapstruct.Mapping;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AcademyMapper {
  @Mapping(
      target = "sportIds",
      expression = "java(academy.getSports() != null ? academy.getSports().stream().map(s -> s.getId()).toList() : java.util.Collections.emptyList())")
  AcademyResponse toResponse(Academy academy);

  Academy toEntity(AcademyRequest request);
}

