package com.academy.service;

import com.academy.dto.request.RatingTemplateRequest;
import com.academy.dto.response.RatingTemplateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingTemplateService {
  Page<RatingTemplateResponse> getAllRatingTemplates(Long academyId, Pageable pageable);

  RatingTemplateResponse getRatingTemplateById(Long academyId, Long id);

  RatingTemplateResponse createRatingTemplate(Long academyId, RatingTemplateRequest request);

  RatingTemplateResponse updateRatingTemplate(
      Long academyId, Long id, RatingTemplateRequest request);

  void deleteRatingTemplate(Long academyId, Long id);
}

