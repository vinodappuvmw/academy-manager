package com.academy.service;

import com.academy.dto.request.PeriodisationRequest;
import com.academy.dto.response.PeriodisationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeriodisationService {
  Page<PeriodisationResponse> getAllPeriodisations(Long academyId, Pageable pageable);

  PeriodisationResponse getPeriodisationById(Long academyId, Long id);

  PeriodisationResponse createPeriodisation(Long academyId, PeriodisationRequest request);

  PeriodisationResponse updatePeriodisation(Long academyId, Long id, PeriodisationRequest request);

  void deletePeriodisation(Long academyId, Long id);

  Page<PeriodisationResponse> getPeriodisationsByStudent(
      Long academyId, Long studentId, Pageable pageable);

  Page<PeriodisationResponse> getPeriodisationsByTrainingProgram(
      Long academyId, Long trainingProgramId, Pageable pageable);
}

