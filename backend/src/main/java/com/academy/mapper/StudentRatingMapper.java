package com.academy.mapper;

import com.academy.domain.StudentRating;
import com.academy.dto.request.StudentRatingRequest;
import com.academy.dto.response.StudentRatingResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentRatingMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(target = "studentId", source = "student.id")
  @Mapping(target = "sportId", source = "sport.id")
  @Mapping(target = "trainingSessionId", source = "trainingSession.id")
  @Mapping(target = "ratingTemplateId", source = "ratingTemplate.id")
  @Mapping(target = "ratedByCoachId", source = "ratedByCoach.id")
  StudentRatingResponse toResponse(StudentRating studentRating);

  StudentRating toEntity(StudentRatingRequest request);
}

