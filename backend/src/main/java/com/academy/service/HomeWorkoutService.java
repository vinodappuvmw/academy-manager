package com.academy.service;

import com.academy.dto.request.HomeWorkoutRequest;
import com.academy.dto.response.HomeWorkoutResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HomeWorkoutService {
  Page<HomeWorkoutResponse> getAllHomeWorkouts(Long academyId, Pageable pageable);

  HomeWorkoutResponse getHomeWorkoutById(Long academyId, Long id);

  HomeWorkoutResponse createHomeWorkout(Long academyId, HomeWorkoutRequest request);

  HomeWorkoutResponse updateHomeWorkout(Long academyId, Long id, HomeWorkoutRequest request);

  void deleteHomeWorkout(Long academyId, Long id);

  Page<HomeWorkoutResponse> getHomeWorkoutsByStudent(
      Long academyId, Long studentId, Pageable pageable);

  Page<HomeWorkoutResponse> getHomeWorkoutsByCoach(
      Long academyId, Long coachId, Pageable pageable);

  Page<HomeWorkoutResponse> getHomeWorkoutsBySport(
      Long academyId, Long sportId, Pageable pageable);

  Page<HomeWorkoutResponse> getHomeWorkoutsByStudentAndSport(
      Long academyId, Long studentId, Long sportId, Pageable pageable);
}

