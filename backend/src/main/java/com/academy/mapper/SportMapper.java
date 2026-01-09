package com.academy.mapper;

import com.academy.domain.Sport;
import com.academy.dto.request.SportRequest;
import com.academy.dto.response.SportResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SportMapper {
  SportResponse toResponse(Sport sport);

  Sport toEntity(SportRequest request);
}

