package com.academy.service;

import com.academy.dto.request.CoachSalaryRequest;
import com.academy.dto.response.CoachSalaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoachSalaryService {
  Page<CoachSalaryResponse> getAllCoachSalaries(Long academyId, Pageable pageable);

  CoachSalaryResponse getCoachSalaryById(Long academyId, Long id);

  CoachSalaryResponse createCoachSalary(Long academyId, CoachSalaryRequest request);

  CoachSalaryResponse updateCoachSalary(Long academyId, Long id, CoachSalaryRequest request);

  void deleteCoachSalary(Long academyId, Long id);
}

