package com.academy.mapper;

import com.academy.domain.TrainingCentre;
import com.academy.dto.request.TrainingCentreRequest;
import com.academy.dto.response.TrainingCentreResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingCentreMapper {
  @Mapping(target = "academyId", source = "academy.id")
  TrainingCentreResponse toResponse(TrainingCentre trainingCentre);

  TrainingCentre toEntity(TrainingCentreRequest request);
}

