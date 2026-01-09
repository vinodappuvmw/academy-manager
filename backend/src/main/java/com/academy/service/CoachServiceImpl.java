package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.Coach;
import com.academy.domain.Sport;
import com.academy.dto.request.CoachRequest;
import com.academy.dto.response.CoachResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.CoachNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.CoachMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.CoachRepository;
import com.academy.repository.SportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoachServiceImpl implements CoachService {

  private final CoachRepository coachRepository;
  private final AcademyRepository academyRepository;
  private final SportRepository sportRepository;
  private final CoachMapper coachMapper;

  public CoachServiceImpl(
      CoachRepository coachRepository,
      AcademyRepository academyRepository,
      SportRepository sportRepository,
      CoachMapper coachMapper) {
    this.coachRepository = coachRepository;
    this.academyRepository = academyRepository;
    this.sportRepository = sportRepository;
    this.coachMapper = coachMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<CoachResponse> getAllCoaches(Long academyId, Pageable pageable) {
    return coachRepository.findByAcademyId(academyId, pageable).map(coachMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public CoachResponse getCoachById(Long academyId, Long id) {
    Coach coach =
        coachRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new CoachNotFoundException(academyId, id));
    return coachMapper.toResponse(coach);
  }

  @Override
  @Transactional
  public CoachResponse createCoach(Long academyId, CoachRequest request) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    Coach coach = coachMapper.toEntity(request);
    coach.setAcademy(academy);
    Coach saved = coachRepository.save(coach);
    return coachMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public CoachResponse updateCoach(Long academyId, Long id, CoachRequest request) {
    Coach coach =
        coachRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new CoachNotFoundException(academyId, id));
    coach.setName(request.name());
    coach.setAddress(request.address());
    coach.setPhone(request.phone());
    coach.setEmail(request.email());
    coach.setSportSubject(request.sportSubject());
    coach.setGender(request.gender());
    coach.setYearsExperience(request.yearsExperience());
    coach.setQualifications(request.qualifications());
    coach.setSpecialization(request.specialization());
    coach.setDateOfBirth(request.dateOfBirth());
    coach.setPhotoUrl(request.photoUrl());
    Coach updated = coachRepository.save(coach);
    return coachMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteCoach(Long academyId, Long id) {
    Coach coach =
        coachRepository
            .findByAcademyIdAndId(academyId, id)
            .orElseThrow(() -> new CoachNotFoundException(academyId, id));
    coachRepository.delete(coach);
  }

  @Override
  @Transactional
  public void addSportToCoach(Long academyId, Long coachId, Long sportId) {
    Coach coach =
        coachRepository
            .findByAcademyIdAndId(academyId, coachId)
            .orElseThrow(() -> new CoachNotFoundException(academyId, coachId));
    Sport sport =
        sportRepository
            .findById(sportId)
            .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id: " + sportId));
    if (!coach.getSports().contains(sport)) {
      coach.getSports().add(sport);
      coachRepository.save(coach);
    }
  }

  @Override
  @Transactional
  public void removeSportFromCoach(Long academyId, Long coachId, Long sportId) {
    Coach coach =
        coachRepository
            .findByAcademyIdAndId(academyId, coachId)
            .orElseThrow(() -> new CoachNotFoundException(academyId, coachId));
    Sport sport =
        sportRepository
            .findById(sportId)
            .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id: " + sportId));
    coach.getSports().remove(sport);
    coachRepository.save(coach);
  }
}

