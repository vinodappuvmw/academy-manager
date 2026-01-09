package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.RatingTemplate;
import com.academy.dto.request.RatingTemplateRequest;
import com.academy.dto.response.RatingTemplateResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.RatingTemplateMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.RatingTemplateRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RatingTemplateServiceImpl implements RatingTemplateService {

  private final RatingTemplateRepository ratingTemplateRepository;
  private final AcademyRepository academyRepository;
  private final RatingTemplateMapper ratingTemplateMapper;

  public RatingTemplateServiceImpl(
      RatingTemplateRepository ratingTemplateRepository,
      AcademyRepository academyRepository,
      RatingTemplateMapper ratingTemplateMapper) {
    this.ratingTemplateRepository = ratingTemplateRepository;
    this.academyRepository = academyRepository;
    this.ratingTemplateMapper = ratingTemplateMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RatingTemplateResponse> getAllRatingTemplates(Long academyId, Pageable pageable) {
    return ratingTemplateRepository
        .findByAcademyId(academyId, pageable)
        .map(ratingTemplateMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public RatingTemplateResponse getRatingTemplateById(Long academyId, Long id) {
    RatingTemplate ratingTemplate =
        ratingTemplateRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Rating template not found with id: " + id));
    return ratingTemplateMapper.toResponse(ratingTemplate);
  }

  @Override
  @Transactional
  public RatingTemplateResponse createRatingTemplate(
      Long academyId, RatingTemplateRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    RatingTemplate ratingTemplate = ratingTemplateMapper.toEntity(request);
    ratingTemplate.setAcademy(academy);
    if (request.isActive() == null) {
      ratingTemplate.setIsActive(true);
    }
    RatingTemplate saved = ratingTemplateRepository.save(ratingTemplate);
    return ratingTemplateMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public RatingTemplateResponse updateRatingTemplate(
      Long academyId, Long id, RatingTemplateRequest request) {
    RatingTemplate ratingTemplate =
        ratingTemplateRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Rating template not found with id: " + id));
    ratingTemplate.setName(request.name());
    ratingTemplate.setIsActive(request.isActive() != null ? request.isActive() : true);
    ratingTemplate.setSchema(request.schema());
    RatingTemplate updated = ratingTemplateRepository.save(ratingTemplate);
    return ratingTemplateMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteRatingTemplate(Long academyId, Long id) {
    RatingTemplate ratingTemplate =
        ratingTemplateRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Rating template not found with id: " + id));
    ratingTemplateRepository.delete(ratingTemplate);
  }
}

