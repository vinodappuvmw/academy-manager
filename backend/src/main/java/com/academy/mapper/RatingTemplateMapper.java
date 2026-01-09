package com.academy.mapper;

import com.academy.domain.RatingTemplate;
import com.academy.dto.request.RatingTemplateRequest;
import com.academy.dto.response.RatingTemplateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RatingTemplateMapper {
  @Mapping(target = "academyId", source = "academy.id")
  RatingTemplateResponse toResponse(RatingTemplate ratingTemplate);

  RatingTemplate toEntity(RatingTemplateRequest request);
}

