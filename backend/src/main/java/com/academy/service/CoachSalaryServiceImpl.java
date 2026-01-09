package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.Coach;
import com.academy.domain.CoachSalary;
import com.academy.dto.request.CoachSalaryRequest;
import com.academy.dto.response.CoachSalaryResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.CoachNotFoundException;
import com.academy.exception.DuplicateResourceException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.CoachSalaryMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.CoachRepository;
import com.academy.repository.CoachSalaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class CoachSalaryServiceImpl implements CoachSalaryService {

  private final CoachSalaryRepository coachSalaryRepository;
  private final AcademyRepository academyRepository;
  private final CoachRepository coachRepository;
  private final CoachSalaryMapper coachSalaryMapper;

  public CoachSalaryServiceImpl(
      CoachSalaryRepository coachSalaryRepository,
      AcademyRepository academyRepository,
      CoachRepository coachRepository,
      CoachSalaryMapper coachSalaryMapper) {
    this.coachSalaryRepository = coachSalaryRepository;
    this.academyRepository = academyRepository;
    this.coachRepository = coachRepository;
    this.coachSalaryMapper = coachSalaryMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CoachSalaryResponse> getAllCoachSalaries(Long academyId, Pageable pageable) {
    return coachSalaryRepository
        .findByAcademyId(academyId, pageable)
        .map(coachSalaryMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public CoachSalaryResponse getCoachSalaryById(Long academyId, Long id) {
    CoachSalary coachSalary =
        coachSalaryRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Coach salary not found with id: " + id));
    return coachSalaryMapper.toResponse(coachSalary);
  }

  @Override
  @Transactional
  public CoachSalaryResponse createCoachSalary(Long academyId, CoachSalaryRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));

    Coach coach =
        coachRepository
            .findByAcademyIdAndId(academyId, request.coachId())
            .orElseThrow(() -> new CoachNotFoundException(academyId, request.coachId()));

    // Check for duplicate
    coachSalaryRepository
        .findByAcademyIdAndCoachIdAndYearAndMonth(
            academyId, request.coachId(), request.year(), request.month())
        .ifPresent(
            existing -> {
              throw new DuplicateResourceException(
                  "Coach salary already exists for coach "
                      + request.coachId()
                      + " for "
                      + request.year()
                      + "-"
                      + request.month());
            });

    CoachSalary coachSalary = coachSalaryMapper.toEntity(request);
    coachSalary.setAcademy(academy);
    coachSalary.setCoach(coach);
    if (request.bonusAmount() == null) {
      coachSalary.setBonusAmount(BigDecimal.ZERO);
    }
    if (request.amountPaid() == null) {
      coachSalary.setAmountPaid(BigDecimal.ZERO);
    }

    CoachSalary saved = coachSalaryRepository.save(coachSalary);
    return coachSalaryMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public CoachSalaryResponse updateCoachSalary(Long academyId, Long id, CoachSalaryRequest request) {
    CoachSalary coachSalary =
        coachSalaryRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Coach salary not found with id: " + id));

    coachSalary.setYear(request.year());
    coachSalary.setMonth(request.month());
    coachSalary.setBaseSalary(request.baseSalary());
    coachSalary.setBonusAmount(
        request.bonusAmount() != null ? request.bonusAmount() : BigDecimal.ZERO);
    coachSalary.setAmountPaid(
        request.amountPaid() != null ? request.amountPaid() : BigDecimal.ZERO);
    coachSalary.setDueDate(request.dueDate());
    coachSalary.setPaidDate(request.paidDate());
    coachSalary.setStatus(request.status());
    coachSalary.setNotes(request.notes());

    CoachSalary updated = coachSalaryRepository.save(coachSalary);
    return coachSalaryMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteCoachSalary(Long academyId, Long id) {
    CoachSalary coachSalary =
        coachSalaryRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Coach salary not found with id: " + id));
    coachSalaryRepository.delete(coachSalary);
  }
}

