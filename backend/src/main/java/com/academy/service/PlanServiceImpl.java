package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.Coach;
import com.academy.domain.Plan;
import com.academy.dto.request.PlanRequest;
import com.academy.dto.response.PlanResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.CoachNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.PlanMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.CoachRepository;
import com.academy.repository.PlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanServiceImpl implements PlanService {

  private final PlanRepository planRepository;
  private final AcademyRepository academyRepository;
  private final CoachRepository coachRepository;
  private final PlanMapper planMapper;

  public PlanServiceImpl(
      PlanRepository planRepository,
      AcademyRepository academyRepository,
      CoachRepository coachRepository,
      PlanMapper planMapper) {
    this.planRepository = planRepository;
    this.academyRepository = academyRepository;
    this.coachRepository = coachRepository;
    this.planMapper = planMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PlanResponse> getAllPlans(Long academyId, Pageable pageable) {
    return planRepository.findByAcademyId(academyId, pageable).map(planMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public PlanResponse getPlanById(Long academyId, Long id) {
    Plan plan =
        planRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
    return planMapper.toResponse(plan);
  }

  @Override
  @Transactional
  public PlanResponse createPlan(Long academyId, PlanRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    Plan plan = planMapper.toEntity(request);
    plan.setAcademy(academy);

    if (request.coachId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.coachId())
              .orElseThrow(() -> new CoachNotFoundException(academyId, request.coachId()));
      plan.setCoach(coach);
    }

    plan.setImageUrl(request.imageUrl());

    Plan saved = planRepository.save(plan);
    return planMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public PlanResponse updatePlan(Long academyId, Long id, PlanRequest request) {
    Plan plan =
        planRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
    plan.setName(request.name());
    plan.setDescription(request.description());
    plan.setImageUrl(request.imageUrl());

    if (request.coachId() != null) {
      Coach coach =
          coachRepository
              .findByAcademyIdAndId(academyId, request.coachId())
              .orElseThrow(() -> new CoachNotFoundException(academyId, request.coachId()));
      plan.setCoach(coach);
    } else {
      plan.setCoach(null);
    }

    Plan updated = planRepository.save(plan);
    return planMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deletePlan(Long academyId, Long id) {
    Plan plan =
        planRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
    planRepository.delete(plan);
  }
}

