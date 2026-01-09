package com.academy.service;

import com.academy.domain.*;
import com.academy.dto.request.PeriodisationRequest;
import com.academy.dto.response.PeriodisationResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.BusinessRuleViolationException;
import com.academy.exception.CoachNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.exception.StudentNotFoundException;
import com.academy.mapper.PeriodisationMapper;
import com.academy.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PeriodisationServiceImpl implements PeriodisationService {

  private final PeriodisationRepository periodisationRepository;
  private final AcademyRepository academyRepository;
  private final StudentRepository studentRepository;
  private final TrainingProgramRepository trainingProgramRepository;
  private final CoachRepository coachRepository;
  private final PeriodisationMapper periodisationMapper;

  public PeriodisationServiceImpl(
      PeriodisationRepository periodisationRepository,
      AcademyRepository academyRepository,
      StudentRepository studentRepository,
      TrainingProgramRepository trainingProgramRepository,
      CoachRepository coachRepository,
      PeriodisationMapper periodisationMapper) {
    this.periodisationRepository = periodisationRepository;
    this.academyRepository = academyRepository;
    this.studentRepository = studentRepository;
    this.trainingProgramRepository = trainingProgramRepository;
    this.coachRepository = coachRepository;
    this.periodisationMapper = periodisationMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PeriodisationResponse> getAllPeriodisations(Long academyId, Pageable pageable) {
    return periodisationRepository
        .findByAcademyId(academyId, pageable)
        .map(periodisationMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public PeriodisationResponse getPeriodisationById(Long academyId, Long id) {
    Periodisation periodisation =
        periodisationRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Periodisation not found with id: " + id));
    return periodisationMapper.toResponse(periodisation);
  }

  @Override
  @Transactional
  public PeriodisationResponse createPeriodisation(
      Long academyId, PeriodisationRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));

    Periodisation periodisation = periodisationMapper.toEntity(request);
    periodisation.setAcademy(academy);

    // Validate and set either student or training program
    if (request.studentId() != null && request.trainingProgramId() == null) {
      Student student =
          studentRepository
              .findByAcademyIdAndId(academyId, request.studentId())
              .orElseThrow(() -> new StudentNotFoundException(academyId, request.studentId()));
      periodisation.setStudent(student);
      periodisation.setTrainingProgram(null);
    } else if (request.trainingProgramId() != null && request.studentId() == null) {
      TrainingProgram trainingProgram =
          trainingProgramRepository
              .findByAcademyIdAndId(academyId, request.trainingProgramId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training program not found with id: " + request.trainingProgramId()));
      periodisation.setTrainingProgram(trainingProgram);
      periodisation.setStudent(null);
    } else {
      throw new BusinessRuleViolationException(
          "Either studentId or trainingProgramId must be provided, but not both");
    }

    if (request.createdByCoachId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.createdByCoachId())
              .orElseThrow(() -> new CoachNotFoundException(academyId, request.createdByCoachId()));
      periodisation.setCreatedByCoach(coach);
    }

    Periodisation saved = periodisationRepository.save(periodisation);
    return periodisationMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public PeriodisationResponse updatePeriodisation(
      Long academyId, Long id, PeriodisationRequest request) {
    Periodisation periodisation =
        periodisationRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Periodisation not found with id: " + id));

    periodisation.setWeeklyPlan(request.weeklyPlan());
    periodisation.setMonthlyPlan(request.monthlyPlan());
    periodisation.setYearlyPlan(request.yearlyPlan());
    periodisation.setWeeklyPlanImageUrl(request.weeklyPlanImageUrl());
    periodisation.setMonthlyPlanImageUrl(request.monthlyPlanImageUrl());
    periodisation.setYearlyPlanImageUrl(request.yearlyPlanImageUrl());

    // Update student or training program if changed
    if (request.studentId() != null && request.trainingProgramId() == null) {
      Student student =
          studentRepository
              .findByAcademyIdAndId(academyId, request.studentId())
              .orElseThrow(() -> new StudentNotFoundException(academyId, request.studentId()));
      periodisation.setStudent(student);
      periodisation.setTrainingProgram(null);
    } else if (request.trainingProgramId() != null && request.studentId() == null) {
      TrainingProgram trainingProgram =
          trainingProgramRepository
              .findByAcademyIdAndId(academyId, request.trainingProgramId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training program not found with id: " + request.trainingProgramId()));
      periodisation.setTrainingProgram(trainingProgram);
      periodisation.setStudent(null);
    } else {
      throw new BusinessRuleViolationException(
          "Either studentId or trainingProgramId must be provided, but not both");
    }

    if (request.createdByCoachId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.createdByCoachId())
              .orElseThrow(() -> new CoachNotFoundException(academyId, request.createdByCoachId()));
      periodisation.setCreatedByCoach(coach);
    } else {
      periodisation.setCreatedByCoach(null);
    }

    Periodisation updated = periodisationRepository.save(periodisation);
    return periodisationMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deletePeriodisation(Long academyId, Long id) {
    Periodisation periodisation =
        periodisationRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Periodisation not found with id: " + id));
    periodisationRepository.delete(periodisation);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PeriodisationResponse> getPeriodisationsByStudent(
      Long academyId, Long studentId, Pageable pageable) {
    return periodisationRepository
        .findByAcademyIdAndStudentId(academyId, studentId, pageable)
        .map(periodisationMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PeriodisationResponse> getPeriodisationsByTrainingProgram(
      Long academyId, Long trainingProgramId, Pageable pageable) {
    return periodisationRepository
        .findByAcademyIdAndTrainingProgramId(academyId, trainingProgramId, pageable)
        .map(periodisationMapper::toResponse);
  }
}

