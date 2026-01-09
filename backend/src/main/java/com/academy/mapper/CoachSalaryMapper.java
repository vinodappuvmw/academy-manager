package com.academy.mapper;

import com.academy.domain.CoachSalary;
import com.academy.dto.request.CoachSalaryRequest;
import com.academy.dto.response.CoachSalaryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoachSalaryMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(target = "coachId", source = "coach.id")
  CoachSalaryResponse toResponse(CoachSalary coachSalary);

  CoachSalary toEntity(CoachSalaryRequest request);
}

