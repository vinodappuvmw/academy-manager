package com.academy.service;

import com.academy.dto.request.AcademyRequest;
import com.academy.dto.response.AcademyResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AcademyService {
  Page<AcademyResponse> getAllAcademies(Pageable pageable);

  AcademyResponse getAcademyById(Long id);

  AcademyResponse createAcademy(AcademyRequest request);

  AcademyResponse updateAcademy(Long id, AcademyRequest request);

  void deleteAcademy(Long id);

  void addSportToAcademy(Long academyId, Long sportId);

  void removeSportFromAcademy(Long academyId, Long sportId);
}

