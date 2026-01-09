package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.TrainingProgram;
import com.academy.dto.request.TrainingProgramRequest;
import com.academy.dto.response.TrainingProgramResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.TrainingProgramMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.TrainingProgramRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TrainingProgramServiceImpl implements TrainingProgramService {

  private final TrainingProgramRepository trainingProgramRepository;
  private final AcademyRepository academyRepository;
  private final TrainingProgramMapper trainingProgramMapper;

  public TrainingProgramServiceImpl(
      TrainingProgramRepository trainingProgramRepository,
      AcademyRepository academyRepository,
      TrainingProgramMapper trainingProgramMapper) {
    this.trainingProgramRepository = trainingProgramRepository;
    this.academyRepository = academyRepository;
    this.trainingProgramMapper = trainingProgramMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TrainingProgramResponse> getAllTrainingPrograms(Long academyId, Pageable pageable) {
    return trainingProgramRepository
        .findByAcademyId(academyId, pageable)
        .map(trainingProgramMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public TrainingProgramResponse getTrainingProgramById(Long academyId, Long id) {
    TrainingProgram trainingProgram =
        trainingProgramRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training program not found with id: " + id));
    return trainingProgramMapper.toResponse(trainingProgram);
  }

  @Override
  @Transactional
  public TrainingProgramResponse createTrainingProgram(
      Long academyId, TrainingProgramRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    TrainingProgram trainingProgram = trainingProgramMapper.toEntity(request);
    trainingProgram.setAcademy(academy);
    TrainingProgram saved = trainingProgramRepository.save(trainingProgram);
    return trainingProgramMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public TrainingProgramResponse updateTrainingProgram(
      Long academyId, Long id, TrainingProgramRequest request) {
    TrainingProgram trainingProgram =
        trainingProgramRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training program not found with id: " + id));
    trainingProgram.setName(request.name());
    trainingProgram.setDescription(request.description());
    TrainingProgram updated = trainingProgramRepository.save(trainingProgram);
    return trainingProgramMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteTrainingProgram(Long academyId, Long id) {
    TrainingProgram trainingProgram =
        trainingProgramRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training program not found with id: " + id));
    trainingProgramRepository.delete(trainingProgram);
  }
}

