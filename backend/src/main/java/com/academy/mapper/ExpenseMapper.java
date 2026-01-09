package com.academy.mapper;

import com.academy.domain.Expense;
import com.academy.dto.request.ExpenseRequest;
import com.academy.dto.response.ExpenseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ExpenseMapper {
  @Mapping(target = "academyId", source = "academy.id")
  @Mapping(target = "trainingCentreId", source = "trainingCentre.id")
  ExpenseResponse toResponse(Expense expense);

  Expense toEntity(ExpenseRequest request);
}

