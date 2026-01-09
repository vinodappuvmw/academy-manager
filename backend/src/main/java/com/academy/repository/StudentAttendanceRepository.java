package com.academy.repository;

import com.academy.domain.StudentAttendance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, Long> {
  Page<StudentAttendance> findByAcademyId(Long academyId, Pageable pageable);

  Optional<StudentAttendance> findByAcademyIdAndId(Long academyId, Long id);

  Page<StudentAttendance> findByTrainingSessionId(Long trainingSessionId, Pageable pageable);

  Optional<StudentAttendance> findByTrainingSessionIdAndStudentId(
      Long trainingSessionId, Long studentId);
}

