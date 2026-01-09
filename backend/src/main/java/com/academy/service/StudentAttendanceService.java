package com.academy.service;

import com.academy.dto.request.StudentAttendanceRequest;
import com.academy.dto.response.StudentAttendanceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentAttendanceService {
  Page<StudentAttendanceResponse> getAllStudentAttendances(Long academyId, Pageable pageable);

  StudentAttendanceResponse getStudentAttendanceById(Long academyId, Long id);

  StudentAttendanceResponse createStudentAttendance(
      Long academyId, StudentAttendanceRequest request);

  StudentAttendanceResponse updateStudentAttendance(
      Long academyId, Long id, StudentAttendanceRequest request);

  void deleteStudentAttendance(Long academyId, Long id);
}

