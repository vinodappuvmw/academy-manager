package com.academy.mapper;

import com.academy.domain.Student;
import com.academy.dto.request.StudentRequest;
import com.academy.dto.response.StudentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(
      target = "sportIds",
      expression = "java(student.getSports() != null ? student.getSports().stream().map(s -> s.getId()).toList() : java.util.Collections.emptyList())")
  StudentResponse toResponse(Student student);

  Student toEntity(StudentRequest request);
}

