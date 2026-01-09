package com.academy.repository;

import com.academy.domain.StudentFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentFeeRepository extends JpaRepository<StudentFee, Long> {
  Page<StudentFee> findByAcademyId(Long academyId, Pageable pageable);

  Optional<StudentFee> findByAcademyIdAndId(Long academyId, Long id);

  Page<StudentFee> findByAcademyIdAndStudentId(Long academyId, Long studentId, Pageable pageable);

  Optional<StudentFee> findByAcademyIdAndStudentIdAndYearAndMonth(
      Long academyId, Long studentId, Integer year, Integer month);
}

