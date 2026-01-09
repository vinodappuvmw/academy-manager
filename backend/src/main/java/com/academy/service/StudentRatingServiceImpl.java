package com.academy.service;

import com.academy.domain.*;
import com.academy.dto.request.StudentRatingRequest;
import com.academy.dto.response.StudentRatingResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.exception.StudentNotFoundException;
import com.academy.mapper.StudentRatingMapper;
import com.academy.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentRatingServiceImpl implements StudentRatingService {

  private final StudentRatingRepository studentRatingRepository;
  private final AcademyRepository academyRepository;
  private final StudentRepository studentRepository;
  private final SportRepository sportRepository;
  private final TrainingSessionRepository trainingSessionRepository;
  private final RatingTemplateRepository ratingTemplateRepository;
  private final CoachRepository coachRepository;
  private final StudentRatingMapper studentRatingMapper;

  public StudentRatingServiceImpl(
      StudentRatingRepository studentRatingRepository,
      AcademyRepository academyRepository,
      StudentRepository studentRepository,
      SportRepository sportRepository,
      TrainingSessionRepository trainingSessionRepository,
      RatingTemplateRepository ratingTemplateRepository,
      CoachRepository coachRepository,
      StudentRatingMapper studentRatingMapper) {
    this.studentRatingRepository = studentRatingRepository;
    this.academyRepository = academyRepository;
    this.studentRepository = studentRepository;
    this.sportRepository = sportRepository;
    this.trainingSessionRepository = trainingSessionRepository;
    this.ratingTemplateRepository = ratingTemplateRepository;
    this.coachRepository = coachRepository;
    this.studentRatingMapper = studentRatingMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<StudentRatingResponse> getAllStudentRatings(Long academyId, Pageable pageable) {
    return studentRatingRepository
        .findByAcademyId(academyId, pageable)
        .map(studentRatingMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public StudentRatingResponse getStudentRatingById(Long academyId, Long id) {
    StudentRating studentRating =
        studentRatingRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Student rating not found with id: " + id));
    return studentRatingMapper.toResponse(studentRating);
  }

  @Override
  @Transactional
  public StudentRatingResponse createStudentRating(Long academyId, StudentRatingRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));

    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, request.studentId())
            .orElseThrow(() -> new StudentNotFoundException(academyId, request.studentId()));

    Sport sport =
        sportRepository
            .findById(request.sportId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Sport not found with id: " + request.sportId()));

    RatingTemplate ratingTemplate =
        ratingTemplateRepository
            .findByAcademyIdAndId(academyId, request.ratingTemplateId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException(
                        "Rating template not found with id: " + request.ratingTemplateId()));

    StudentRating studentRating = studentRatingMapper.toEntity(request);
    studentRating.setAcademy(academy);
    studentRating.setStudent(student);
    studentRating.setSport(sport);
    studentRating.setRatingTemplate(ratingTemplate);

    if (request.trainingSessionId() != null) {
      TrainingSession trainingSession =
          trainingSessionRepository
              .findByAcademyIdAndId(academyId, request.trainingSessionId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training session not found with id: " + request.trainingSessionId()));
      studentRating.setTrainingSession(trainingSession);
    }

    if (request.ratedByCoachId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.ratedByCoachId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Coach not found with id: " + request.ratedByCoachId()));
      studentRating.setRatedByCoach(coach);
    }

    StudentRating saved = studentRatingRepository.save(studentRating);
    return studentRatingMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public StudentRatingResponse updateStudentRating(
      Long academyId, Long id, StudentRatingRequest request) {
    StudentRating studentRating =
        studentRatingRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Student rating not found with id: " + id));

    studentRating.setRatingDate(request.ratingDate());
    studentRating.setScores(request.scores());
    studentRating.setComments(request.comments());

    // Update sport
    Sport sport =
        sportRepository
            .findById(request.sportId())
            .orElseThrow(
                () ->
                    new ResourceNotFoundException("Sport not found with id: " + request.sportId()));
    studentRating.setSport(sport);

    if (request.trainingSessionId() != null) {
      TrainingSession trainingSession =
          trainingSessionRepository
              .findByAcademyIdAndId(academyId, request.trainingSessionId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Training session not found with id: " + request.trainingSessionId()));
      studentRating.setTrainingSession(trainingSession);
    } else {
      studentRating.setTrainingSession(null);
    }

    if (request.ratedByCoachId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.ratedByCoachId())
              .orElseThrow(
                  () ->
                      new ResourceNotFoundException(
                          "Coach not found with id: " + request.ratedByCoachId()));
      studentRating.setRatedByCoach(coach);
    } else {
      studentRating.setRatedByCoach(null);
    }

    StudentRating updated = studentRatingRepository.save(studentRating);
    return studentRatingMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteStudentRating(Long academyId, Long id) {
    StudentRating studentRating =
        studentRatingRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Student rating not found with id: " + id));
    studentRatingRepository.delete(studentRating);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<StudentRatingResponse> getStudentRatingHistory(
      Long academyId, Long studentId, Long sportId, Pageable pageable) {
    return studentRatingRepository
        .findByAcademyIdAndStudentIdAndSportIdOrderByRatingDateDesc(
            academyId, studentId, sportId, pageable)
        .map(studentRatingMapper::toResponse);
  }
}

