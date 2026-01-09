package com.academy.service;

import com.academy.dto.request.TrainingProgramRequest;
import com.academy.dto.response.TrainingProgramResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TrainingProgramService {
  Page<TrainingProgramResponse> getAllTrainingPrograms(Long academyId, Pageable pageable);

  TrainingProgramResponse getTrainingProgramById(Long academyId, Long id);

  TrainingProgramResponse createTrainingProgram(Long academyId, TrainingProgramRequest request);

  TrainingProgramResponse updateTrainingProgram(
      Long academyId, Long id, TrainingProgramRequest request);

  void deleteTrainingProgram(Long academyId, Long id);
}

