package com.academy.service;

import com.academy.dto.request.PlanRequest;
import com.academy.dto.response.PlanResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlanService {
  Page<PlanResponse> getAllPlans(Long academyId, Pageable pageable);

  PlanResponse getPlanById(Long academyId, Long id);

  PlanResponse createPlan(Long academyId, PlanRequest request);

  PlanResponse updatePlan(Long academyId, Long id, PlanRequest request);

  void deletePlan(Long academyId, Long id);
}

