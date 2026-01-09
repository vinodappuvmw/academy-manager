package com.academy.mapper;

import com.academy.domain.Plan;
import com.academy.dto.request.PlanRequest;
import com.academy.dto.response.PlanResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlanMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(
      target = "coachId",
      expression = "java(plan.getCoach() != null ? plan.getCoach().getId() : null)")
  PlanResponse toResponse(Plan plan);

  Plan toEntity(PlanRequest request);
}

