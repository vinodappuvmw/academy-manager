package com.academy.service;

import com.academy.dto.request.StudentFeeRequest;
import com.academy.dto.response.StudentFeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentFeeService {
  Page<StudentFeeResponse> getAllStudentFees(Long academyId, Pageable pageable);

  StudentFeeResponse getStudentFeeById(Long academyId, Long id);

  StudentFeeResponse createStudentFee(Long academyId, StudentFeeRequest request);

  StudentFeeResponse updateStudentFee(Long academyId, Long id, StudentFeeRequest request);

  void deleteStudentFee(Long academyId, Long id);
}

