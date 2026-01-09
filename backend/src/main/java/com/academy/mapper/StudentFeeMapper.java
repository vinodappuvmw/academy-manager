package com.academy.mapper;

import com.academy.domain.StudentFee;
import com.academy.dto.request.StudentFeeRequest;
import com.academy.dto.response.StudentFeeResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudentFeeMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(target = "studentId", source = "student.id")
  StudentFeeResponse toResponse(StudentFee studentFee);

  StudentFee toEntity(StudentFeeRequest request);
}

