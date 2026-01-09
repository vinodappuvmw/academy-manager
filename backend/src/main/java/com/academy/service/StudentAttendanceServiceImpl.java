package com.academy.service;

import com.academy.domain.*;
import com.academy.dto.request.StudentAttendanceRequest;
import com.academy.dto.response.StudentAttendanceResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.DuplicateResourceException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.exception.StudentNotFoundException;
import com.academy.mapper.StudentAttendanceMapper;
import com.academy.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentAttendanceServiceImpl implements StudentAttendanceService {

  private final StudentAttendanceRepository studentAttendanceRepository;
  private final AcademyRepository academyRepository;
  private final TrainingSessionRepository trainingSessionRepository;
  private final StudentRepository studentRepository;
  private final CoachRepository coachRepository;
  private final StudentAttendanceMapper studentAttendanceMapper;

  public StudentAttendanceServiceImpl(
      StudentAttendanceRepository studentAttendanceRepository,
      AcademyRepository academyRepository,
      TrainingSessionRepository trainingSessionRepository,
      StudentRepository studentRepository,
      CoachRepository coachRepository,
      StudentAttendanceMapper studentAttendanceMapper) {
    this.studentAttendanceRepository = studentAttendanceRepository;
    this.academyRepository = academyRepository;
    this.trainingSessionRepository = trainingSessionRepository;
    this.studentRepository = studentRepository;
    this.coachRepository = coachRepository;
    this.studentAttendanceMapper = studentAttendanceMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<StudentAttendanceResponse> getAllStudentAttendances(
      Long academyId, Pageable pageable) {
    return studentAttendanceRepository
        .findByAcademyId(academyId, pageable)
        .map(studentAttendanceMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public StudentAttendanceResponse getStudentAttendanceById(Long academyId, Long id) {
    StudentAttendance studentAttendance =
        studentAttendanceRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Student attendance not found with id: " + id));
    return studentAttendanceMapper.toResponse(studentAttendance);
  }

  @Override
  @Transactional
  public StudentAttendanceResponse createStudentAttendance(
      Long academyId, StudentAttendanceRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));

    TrainingSession trainingSession =
        trainingSessionRepository
            .findByAcademyIdAndId(academyId, request.trainingSessionId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Training session not found with id: " + request.trainingSessionId()));

    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, request.studentId())
            .orElseThrow(() -> new StudentNotFoundException(academyId, request.studentId()));

    // Check for duplicate
    studentAttendanceRepository
        .findByTrainingSessionIdAndStudentId(request.trainingSessionId(), request.studentId())
        .ifPresent(
            existing -> {
              throw new DuplicateResourceException(
                  "Attendance already exists for student "
                      + request.studentId()
                      + " in session "
                      + request.trainingSessionId());
            });

    StudentAttendance studentAttendance = studentAttendanceMapper.toEntity(request);
    studentAttendance.setAcademy(academy);
    studentAttendance.setTrainingSession(trainingSession);
    studentAttendance.setStudent(student);

    if (request.markedByCoachId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.markedByCoachId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Coach not found with id: " + request.markedByCoachId()));
      studentAttendance.setMarkedByCoach(coach);
    }

    StudentAttendance saved = studentAttendanceRepository.save(studentAttendance);
    return studentAttendanceMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public StudentAttendanceResponse updateStudentAttendance(
      Long academyId, Long id, StudentAttendanceRequest request) {
    StudentAttendance studentAttendance =
        studentAttendanceRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Student attendance not found with id: " + id));

    studentAttendance.setStatus(request.status());
    studentAttendance.setNotes(request.notes());

    if (request.markedByCoachId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.markedByCoachId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Coach not found with id: " + request.markedByCoachId()));
      studentAttendance.setMarkedByCoach(coach);
    } else {
      studentAttendance.setMarkedByCoach(null);
    }

    StudentAttendance updated = studentAttendanceRepository.save(studentAttendance);
    return studentAttendanceMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteStudentAttendance(Long academyId, Long id) {
    StudentAttendance studentAttendance =
        studentAttendanceRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Student attendance not found with id: " + id));
    studentAttendanceRepository.delete(studentAttendance);
  }
}

