package com.academy.service;

import com.academy.domain.*;
import com.academy.dto.request.TrainingSessionRequest;
import com.academy.dto.response.TrainingSessionResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.BusinessRuleViolationException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.TrainingSessionMapper;
import com.academy.repository.*;
import com.academy.domain.Sport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingSessionServiceImpl implements TrainingSessionService {

  private final TrainingSessionRepository trainingSessionRepository;
  private final AcademyRepository academyRepository;
  private final TrainingCentreRepository trainingCentreRepository;
  private final TrainingProgramRepository trainingProgramRepository;
  private final PlanRepository planRepository;
  private final CoachRepository coachRepository;
  private final StudentRepository studentRepository;
  private final SportRepository sportRepository;
  private final TrainingSessionMapper trainingSessionMapper;

  public TrainingSessionServiceImpl(
      TrainingSessionRepository trainingSessionRepository,
      AcademyRepository academyRepository,
      TrainingCentreRepository trainingCentreRepository,
      TrainingProgramRepository trainingProgramRepository,
      PlanRepository planRepository,
      CoachRepository coachRepository,
      StudentRepository studentRepository,
      SportRepository sportRepository,
      TrainingSessionMapper trainingSessionMapper) {
    this.trainingSessionRepository = trainingSessionRepository;
    this.academyRepository = academyRepository;
    this.trainingCentreRepository = trainingCentreRepository;
    this.trainingProgramRepository = trainingProgramRepository;
    this.planRepository = planRepository;
    this.coachRepository = coachRepository;
    this.studentRepository = studentRepository;
    this.sportRepository = sportRepository;
    this.trainingSessionMapper = trainingSessionMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TrainingSessionResponse> getAllTrainingSessions(Long academyId, Pageable pageable) {
    return trainingSessionRepository
        .findByAcademyId(academyId, pageable)
        .map(trainingSessionMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public TrainingSessionResponse getTrainingSessionById(Long academyId, Long id) {
    TrainingSession trainingSession =
        trainingSessionRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training session not found with id: " + id));
    return trainingSessionMapper.toResponse(trainingSession);
  }

  @Override
  @Transactional
  public TrainingSessionResponse createTrainingSession(
      Long academyId, TrainingSessionRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));

    TrainingSession trainingSession = trainingSessionMapper.toEntity(request);
    trainingSession.setAcademy(academy);

    if (request.trainingCentreId() != null) {
      TrainingCentre trainingCentre =
          trainingCentreRepository
              .findByAcademyIdAndId(academyId, request.trainingCentreId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training centre not found with id: " + request.trainingCentreId()));
      trainingSession.setTrainingCentre(trainingCentre);
    }

    if (request.programId() != null) {
      TrainingProgram program =
          trainingProgramRepository
              .findByAcademyIdAndId(academyId, request.programId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training program not found with id: " + request.programId()));
      trainingSession.setProgram(program);
    }

    if (request.planId() != null) {
      Plan plan =
          planRepository
              .findByAcademyIdAndId(academyId, request.planId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException("Plan not found with id: " + request.planId()));
      trainingSession.setPlan(plan);
    }

    if (request.sessionOwnerId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.sessionOwnerId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Coach not found with id: " + request.sessionOwnerId()));
      trainingSession.setSessionOwner(coach);
    }

    if (request.sportId() != null) {
      Sport sport =
          sportRepository
              .findById(request.sportId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Sport not found with id: " + request.sportId()));
      trainingSession.setSport(sport);
    }

    if (request.coachIds() != null && !request.coachIds().isEmpty()) {
      List<Coach> coaches = new ArrayList<>();
      for (Long coachId : request.coachIds()) {
        Coach coach =
            coachRepository
                .findByAcademyIdAndId(academyId, coachId)
                .orElseThrow(
                    () ->
                        new BusinessRuleViolationException(
                            "Coach not found with id: " + coachId + " in academy: " + academyId));
        coaches.add(coach);
      }
      trainingSession.setCoaches(coaches);
    }

    if (request.studentIds() != null && !request.studentIds().isEmpty()) {
      List<Student> students = new ArrayList<>();
      for (Long studentId : request.studentIds()) {
        Student student =
            studentRepository
                .findByAcademyIdAndId(academyId, studentId)
                .orElseThrow(
                    () ->
                        new BusinessRuleViolationException(
                            "Student not found with id: " + studentId + " in academy: " + academyId));
        students.add(student);
      }
      trainingSession.setStudents(students);
    }

    TrainingSession saved = trainingSessionRepository.save(trainingSession);
    return trainingSessionMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public TrainingSessionResponse updateTrainingSession(
      Long academyId, Long id, TrainingSessionRequest request) {
    TrainingSession trainingSession =
        trainingSessionRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training session not found with id: " + id));

    trainingSession.setDate(request.date());
    trainingSession.setStartTime(request.startTime());
    trainingSession.setDurationMinutes(request.durationMinutes());
    trainingSession.setNotes(request.notes());

    if (request.trainingCentreId() != null) {
      TrainingCentre trainingCentre =
          trainingCentreRepository
              .findByAcademyIdAndId(academyId, request.trainingCentreId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training centre not found with id: " + request.trainingCentreId()));
      trainingSession.setTrainingCentre(trainingCentre);
    } else {
      trainingSession.setTrainingCentre(null);
    }

    if (request.programId() != null) {
      TrainingProgram program =
          trainingProgramRepository
              .findByAcademyIdAndId(academyId, request.programId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training program not found with id: " + request.programId()));
      trainingSession.setProgram(program);
    } else {
      trainingSession.setProgram(null);
    }

    if (request.planId() != null) {
      Plan plan =
          planRepository
              .findByAcademyIdAndId(academyId, request.planId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException("Plan not found with id: " + request.planId()));
      trainingSession.setPlan(plan);
    } else {
      trainingSession.setPlan(null);
    }

    if (request.sessionOwnerId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.sessionOwnerId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Coach not found with id: " + request.sessionOwnerId()));
      trainingSession.setSessionOwner(coach);
    } else {
      trainingSession.setSessionOwner(null);
    }

    if (request.sportId() != null) {
      Sport sport =
          sportRepository
              .findById(request.sportId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Sport not found with id: " + request.sportId()));
      trainingSession.setSport(sport);
    } else {
      trainingSession.setSport(null);
    }

    if (request.coachIds() != null) {
      List<Coach> coaches = new ArrayList<>();
      for (Long coachId : request.coachIds()) {
        Coach coach =
            coachRepository
                .findByAcademyIdAndId(academyId, coachId)
                .orElseThrow(
                    () ->
                        new BusinessRuleViolationException(
                            "Coach not found with id: " + coachId + " in academy: " + academyId));
        coaches.add(coach);
      }
      trainingSession.setCoaches(coaches);
    }

    if (request.studentIds() != null) {
      List<Student> students = new ArrayList<>();
      for (Long studentId : request.studentIds()) {
        Student student =
            studentRepository
                .findByAcademyIdAndId(academyId, studentId)
                .orElseThrow(
                    () ->
                        new BusinessRuleViolationException(
                            "Student not found with id: " + studentId + " in academy: " + academyId));
        students.add(student);
      }
      trainingSession.setStudents(students);
    }

    TrainingSession updated = trainingSessionRepository.save(trainingSession);
    return trainingSessionMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteTrainingSession(Long academyId, Long id) {
    TrainingSession trainingSession =
        trainingSessionRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Training session not found with id: " + id));
    trainingSessionRepository.delete(trainingSession);
  }
}

