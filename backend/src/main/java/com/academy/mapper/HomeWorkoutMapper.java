package com.academy.mapper;

import com.academy.domain.HomeWorkout;
import com.academy.dto.request.HomeWorkoutRequest;
import com.academy.dto.response.HomeWorkoutResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HomeWorkoutMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(target = "coachId", source = "coach.id")
  @Mapping(target = "studentId", source = "student.id")
  @Mapping(target = "sportId", source = "sport.id")
  HomeWorkoutResponse toResponse(HomeWorkout homeWorkout);

  HomeWorkout toEntity(HomeWorkoutRequest request);
}

