package com.academy.mapper;

import com.academy.domain.Coach;
import com.academy.dto.request.CoachRequest;
import com.academy.dto.response.CoachResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(
      target = "sportIds",
      expression = "java(coach.getSports() != null ? coach.getSports().stream().map(s -> s.getId()).toList() : java.util.Collections.emptyList())")
  CoachResponse toResponse(Coach coach);

  Coach toEntity(CoachRequest request);
}

