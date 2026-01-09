package com.academy.service;

import com.academy.dto.request.StudentRequest;
import com.academy.dto.response.StudentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
  Page<StudentResponse> getAllStudents(Long academyId, Pageable pageable);

  StudentResponse getStudentById(Long academyId, Long id);

  StudentResponse createStudent(Long academyId, StudentRequest request);

  StudentResponse updateStudent(Long academyId, Long id, StudentRequest request);

  void deleteStudent(Long academyId, Long id);

  void enrollStudentInSport(Long academyId, Long studentId, Long sportId);

  void unenrollStudentFromSport(Long academyId, Long studentId, Long sportId);
}

