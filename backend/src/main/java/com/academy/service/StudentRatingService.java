package com.academy.service;

import com.academy.dto.request.StudentRatingRequest;
import com.academy.dto.response.StudentRatingResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentRatingService {
  Page<StudentRatingResponse> getAllStudentRatings(Long academyId, Pageable pageable);

  StudentRatingResponse getStudentRatingById(Long academyId, Long id);

  StudentRatingResponse createStudentRating(Long academyId, StudentRatingRequest request);

  StudentRatingResponse updateStudentRating(Long academyId, Long id, StudentRatingRequest request);

  void deleteStudentRating(Long academyId, Long id);

  Page<StudentRatingResponse> getStudentRatingHistory(
      Long academyId, Long studentId, Long sportId, Pageable pageable);
}

