package com.academy.service;

import com.academy.dto.request.SportRequest;
import com.academy.dto.response.SportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SportService {
  Page<SportResponse> getAllSports(Pageable pageable);

  SportResponse getSportById(Long id);

  SportResponse createSport(SportRequest request);

  SportResponse updateSport(Long id, SportRequest request);

  void deleteSport(Long id);
}

