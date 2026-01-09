package com.academy.mapper;

import com.academy.domain.TrainingSession;
import com.academy.dto.request.TrainingSessionRequest;
import com.academy.dto.response.TrainingSessionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingSessionMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(target = "trainingCentreId", source = "trainingCentre.id")
  @Mapping(target = "programId", source = "program.id")
  @Mapping(target = "planId", source = "plan.id")
  @Mapping(target = "sessionOwnerId", source = "sessionOwner.id")
  @Mapping(target = "sportId", source = "sport.id")
  @Mapping(target = "coachIds", expression = "java(trainingSession.getCoaches() != null ? trainingSession.getCoaches().stream().map(c -> c.getId()).toList() : java.util.Collections.emptyList())")
  @Mapping(target = "studentIds", expression = "java(trainingSession.getStudents() != null ? trainingSession.getStudents().stream().map(s -> s.getId()).toList() : java.util.Collections.emptyList())")
  TrainingSessionResponse toResponse(TrainingSession trainingSession);

  TrainingSession toEntity(TrainingSessionRequest request);
}

