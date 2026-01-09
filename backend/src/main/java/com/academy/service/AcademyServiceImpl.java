package com.academy.service;

import com.academy.domain.Academy;
import com.academy.domain.Sport;
import com.academy.dto.request.AcademyRequest;
import com.academy.dto.response.AcademyResponse;
import com.academy.exception.AcademyNotFoundException;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.AcademyMapper;
import com.academy.repository.AcademyRepository;
import com.academy.repository.SportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AcademyServiceImpl implements AcademyService {

  private final AcademyRepository academyRepository;
  private final SportRepository sportRepository;
  private final AcademyMapper academyMapper;

  public AcademyServiceImpl(
      AcademyRepository academyRepository,
      SportRepository sportRepository,
      AcademyMapper academyMapper) {
    this.academyRepository = academyRepository;
    this.sportRepository = sportRepository;
    this.academyMapper = academyMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AcademyResponse> getAllAcademies(Pageable pageable) {
    return academyRepository.findAll(pageable).map(academyMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public AcademyResponse getAcademyById(Long id) {
    Academy academy =
        academyRepository
            .findById(id)
            .orElseThrow(() -> new AcademyNotFoundException(id));
    return academyMapper.toResponse(academy);
  }

  @Override
  @Transactional
  public AcademyResponse createAcademy(AcademyRequest request) {
    Academy academy = academyMapper.toEntity(request);
    Academy saved = academyRepository.save(academy);
    return academyMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public AcademyResponse updateAcademy(Long id, AcademyRequest request) {
    Academy academy =
        academyRepository
            .findById(id)
            .orElseThrow(() -> new AcademyNotFoundException(id));
    academy.setName(request.name());
    academy.setAddress(request.address());
    academy.setPhone(request.phone());
    academy.setEmail(request.email());
    academy.setWebsite(request.website());
    academy.setLogoUrl(request.logoUrl());
    Academy updated = academyRepository.save(academy);
    return academyMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteAcademy(Long id) {
    if (!academyRepository.existsById(id)) {
      throw new AcademyNotFoundException(id);
    }
    academyRepository.deleteById(id);
  }

  @Override
  @Transactional
  public void addSportToAcademy(Long academyId, Long sportId) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    Sport sport =
        sportRepository
            .findById(sportId)
            .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id: " + sportId));
    if (!academy.getSports().contains(sport)) {
      academy.getSports().add(sport);
      academyRepository.save(academy);
    }
  }

  @Override
  @Transactional
  public void removeSportFromAcademy(Long academyId, Long sportId) {
    Academy academy =
        academyRepository
            .findById(academyId)
            .orElseThrow(() -> new AcademyNotFoundException(academyId));
    Sport sport =
        sportRepository
            .findById(sportId)
            .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id: " + sportId));
    academy.getSports().remove(sport);
    academyRepository.save(academy);
  }
}

