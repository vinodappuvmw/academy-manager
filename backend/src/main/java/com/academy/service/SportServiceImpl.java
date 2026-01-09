package com.academy.service;

import com.academy.domain.Sport;
import com.academy.dto.request.SportRequest;
import com.academy.dto.response.SportResponse;
import com.academy.exception.ResourceNotFoundException;
import com.academy.mapper.SportMapper;
import com.academy.repository.SportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SportServiceImpl implements SportService {

  private final SportRepository sportRepository;
  private final SportMapper sportMapper;

  public SportServiceImpl(SportRepository sportRepository, SportMapper sportMapper) {
    this.sportRepository = sportRepository;
    this.sportMapper = sportMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<SportResponse> getAllSports(Pageable pageable) {
    return sportRepository.findAll(pageable).map(sportMapper::toResponse);
  }

  @Override
  @Transactional(readOnly = true)
  public SportResponse getSportById(Long id) {
    Sport sport =
        sportRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id: " + id));
    return sportMapper.toResponse(sport);
  }

  @Override
  @Transactional
  public SportResponse createSport(SportRequest request) {
    Sport sport = sportMapper.toEntity(request);
    Sport saved = sportRepository.save(sport);
    return sportMapper.toResponse(saved);
  }

  @Override
  @Transactional
  public SportResponse updateSport(Long id, SportRequest request) {
    Sport sport =
        sportRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Sport not found with id: " + id));
    sport.setName(request.name());
    sport.setDescription(request.description());
    sport.setCategory(request.category());
    Sport updated = sportRepository.save(sport);
    return sportMapper.toResponse(updated);
  }

  @Override
  @Transactional
  public void deleteSport(Long id) {
    if (!sportRepository.existsById(id)) {
      throw new ResourceNotFoundException("Sport not found with id: " + id);
    }
    sportRepository.deleteById(id);
  }
}

