package com.academy.service;

import com.academy.domain.*;
import com.academy.dto.request.HomeWorkoutRequest;
import com.academy.dto.response.HomeWorkoutResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.CoachNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.exception.StudentNotFoundException;
import com.academy.mapper.HomeWorkoutMapper;
import com.academy.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HomeWorkoutServiceImpl implements HomeWorkoutService {

  private final HomeWorkoutRepository homeWorkoutRepository;
  private final AcademyRepository academyRepository;
  private final CoachRepository coachRepository;
  private final StudentRepository studentRepository;
  private final SportRepository sportRepository;
  private final HomeWorkoutMapper homeWorkoutMapper;

  public HomeWorkoutServiceImpl(
      HomeWorkoutRepository homeWorkoutRepository,
      AcademyRepository academyRepository,
      CoachRepository coachRepository,
      StudentRepository studentRepository,
      SportRepository sportRepository,
      HomeWorkoutMapper homeWorkoutMapper) {
    this.homeWorkoutRepository = homeWorkoutRepository;
    this.academyRepository = academyRepository;
    this.coachRepository = coachRepository;
    this.studentRepository = studentRepository;
    this.sportRepository = sportRepository;
    this.homeWorkoutMapper = homeWorkoutMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<HomeWorkoutResponse> getAllHomeWorkouts(Long academyId, Pageable pageable) {
    return homeWorkoutRepository
        .findByAcademyId(academyId, pageable)
        .map(homeWorkoutMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public HomeWorkoutResponse getHomeWorkoutById(Long academyId, Long id) {
    HomeWorkout homeWorkout =
        homeWorkoutRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Home workout not found with id: " + id));
    return homeWorkoutMapper.toResponse(homeWorkout);
  }

  @Override
  @Transactional
  public HomeWorkoutResponse createHomeWorkout(Long academyId, HomeWorkoutRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));

    Coach coach =
        coachRepository
            .findByAcademyIdAndId(academyId, request.coachId())
            .orElseThrow(() -> new CoachNotFoundException(academyId, request.coachId()));

    Student student =
        studentRepository
            .findByAcademyIdAndId(academyId, request.studentId())
            .orElseThrow(() -> new StudentNotFoundException(academyId, request.studentId()));

    Sport sport =
        sportRepository
            .findById(request.sportId())
            .orElseThrow(
                () -> new ResourceNotFoundException("Sport not found with id: " + request.sportId()));

    HomeWorkout homeWorkout = homeWorkoutMapper.toEntity(request);
    homeWorkout.setAcademy(academy);
    homeWorkout.setCoach(coach);
    homeWorkout.setStudent(student);
    homeWorkout.setSport(sport);

    HomeWorkout saved = homeWorkoutRepository.save(homeWorkout);
    return homeWorkoutMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public HomeWorkoutResponse updateHomeWorkout(Long academyId, Long id, HomeWorkoutRequest request) {
    HomeWorkout homeWorkout =
        homeWorkoutRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Home workout not found with id: " + id));

    homeWorkout.setTitle(request.title());
    homeWorkout.setDescription(request.description());
    homeWorkout.setVideoLinks(request.videoLinks());
    homeWorkout.setImageUrl(request.imageUrl());
    homeWorkout.setDifficultyLevel(request.difficultyLevel());
    homeWorkout.setDurationMinutes(request.durationMinutes());

    // Update coach if changed
    if (!homeWorkout.getCoach().getId().equals(request.coachId())) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.coachId())
              .orElseThrow(() -> new CoachNotFoundException(academyId, request.coachId()));
      homeWorkout.setCoach(coach);
    }

    // Update student if changed
    if (!homeWorkout.getStudent().getId().equals(request.studentId())) {
      Student student =
          studentRepository
              .findByAcademyIdAndId(academyId, request.studentId())
              .orElseThrow(() -> new StudentNotFoundException(academyId, request.studentId()));
      homeWorkout.setStudent(student);
    }

    // Update sport if changed
    if (!homeWorkout.getSport().getId().equals(request.sportId())) {
      Sport sport =
          sportRepository
              .findById(request.sportId())
              .orElseThrow(
                  () -> new ResourceNotFoundException("Sport not found with id: " + request.sportId()));
      homeWorkout.setSport(sport);
    }

    HomeWorkout updated = homeWorkoutRepository.save(homeWorkout);
    return homeWorkoutMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteHomeWorkout(Long academyId, Long id) {
    HomeWorkout homeWorkout =
        homeWorkoutRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Home workout not found with id: " + id));
    homeWorkoutRepository.delete(homeWorkout);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<HomeWorkoutResponse> getHomeWorkoutsByStudent(
      Long academyId, Long studentId, Pageable pageable) {
    return homeWorkoutRepository
        .findByAcademyIdAndStudentId(academyId, studentId, pageable)
        .map(homeWorkoutMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<HomeWorkoutResponse> getHomeWorkoutsByCoach(
      Long academyId, Long coachId, Pageable pageable) {
    return homeWorkoutRepository
        .findByAcademyIdAndCoachId(academyId, coachId, pageable)
        .map(homeWorkoutMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<HomeWorkoutResponse> getHomeWorkoutsBySport(
      Long academyId, Long sportId, Pageable pageable) {
    return homeWorkoutRepository
        .findByAcademyIdAndSportId(academyId, sportId, pageable)
        .map(homeWorkoutMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<HomeWorkoutResponse> getHomeWorkoutsByStudentAndSport(
      Long academyId, Long studentId, Long sportId, Pageable pageable) {
    return homeWorkoutRepository
        .findByAcademyIdAndStudentIdAndSportId(academyId, studentId, sportId, pageable)
        .map(homeWorkoutMapper::toResponse);
  }
}

