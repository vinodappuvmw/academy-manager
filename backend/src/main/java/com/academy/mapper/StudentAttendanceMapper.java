package com.academy.mapper;

import com.academy.domain.StudentAttendance;
import com.academy.dto.request.StudentAttendanceRequest;
import com.academy.dto.response.StudentAttendanceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentAttendanceMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(target = "trainingSessionId", source = "trainingSession.id")
  @Mapping(target = "studentId", source = "student.id")
  @Mapping(target = "markedByCoachId", source = "markedByCoach.id")
  StudentAttendanceResponse toResponse(StudentAttendance studentAttendance);

  StudentAttendance toEntity(StudentAttendanceRequest request);
}

