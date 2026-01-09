package com.academy.mapper;

import com.academy.domain.TrainingProgram;
import com.academy.dto.request.TrainingProgramRequest;
import com.academy.dto.response.TrainingProgramResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingProgramMapper {
  @Mapping(target = "academyId", source = "academy.id")
  TrainingProgramResponse toResponse(TrainingProgram trainingProgram);

  TrainingProgram toEntity(TrainingProgramRequest request);
}

