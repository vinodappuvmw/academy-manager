package com.academy.service;

import com.academy.dto.request.TrainingCentreRequest;
import com.academy.dto.response.TrainingCentreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainingCentreService {
  Page<TrainingCentreResponse> getAllTrainingCentres(Long academyId, Pageable pageable);

  TrainingCentreResponse getTrainingCentreById(Long academyId, Long id);

  TrainingCentreResponse createTrainingCentre(Long academyId, TrainingCentreRequest request);

  TrainingCentreResponse updateTrainingCentre(
      Long academyId, Long id, TrainingCentreRequest request);

  void deleteTrainingCentre(Long academyId, Long id);
}

