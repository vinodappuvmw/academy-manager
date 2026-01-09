package com.academy.service;

import com.academy.dto.request.TrainingSessionRequest;
import com.academy.dto.response.TrainingSessionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainingSessionService {
  Page<TrainingSessionResponse> getAllTrainingSessions(Long academyId, Pageable pageable);

  TrainingSessionResponse getTrainingSessionById(Long academyId, Long id);

  TrainingSessionResponse createTrainingSession(Long academyId, TrainingSessionRequest request);

  TrainingSessionResponse updateTrainingSession(
      Long academyId, Long id, TrainingSessionRequest request);

  void deleteTrainingSession(Long academyId, Long id);
}

