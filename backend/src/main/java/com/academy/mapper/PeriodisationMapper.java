package com.academy.mapper;

import com.academy.domain.Periodisation;
import com.academy.dto.request.PeriodisationRequest;
import com.academy.dto.response.PeriodisationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PeriodisationMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(
      target = "studentId",
      expression = "java(periodisation.getStudent() != null ? periodisation.getStudent().getId() : null)")
  @Mapping(
      target = "trainingProgramId",
      expression = "java(periodisation.getTrainingProgram() != null ? periodisation.getTrainingProgram().getId() : null)")
  @Mapping(
      target = "createdByCoachId",
      expression = "java(periodisation.getCreatedByCoach() != null ? periodisation.getCreatedByCoach().getId() : null)")
  PeriodisationResponse toResponse(Periodisation periodisation);

  Periodisation toEntity(PeriodisationRequest request);
}

