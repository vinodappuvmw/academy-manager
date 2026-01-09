package com.academy.service;

import com.academy.dto.request.CoachRequest;
import com.academy.dto.response.CoachResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoachService {
  Page<CoachResponse> getAllCoaches(Long academyId, Pageable pageable);

  CoachResponse getCoachById(Long academyId, Long id);

  CoachResponse createCoach(Long academyId, CoachRequest request);

  CoachResponse updateCoach(Long academyId, Long id, CoachRequest request);

  void deleteCoach(Long academyId, Long id);

  void addSportToCoach(Long academyId, Long coachId, Long sportId);

  void removeSportFromCoach(Long academyId, Long coachId, Long sportId);
}

