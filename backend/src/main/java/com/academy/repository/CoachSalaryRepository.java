package com.academy.repository;

import com.academy.domain.CoachSalary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CoachSalaryRepository extends JpaRepository<CoachSalary, Long> {
  Page<CoachSalary> findByAcademyId(Long academyId, Pageable pageable);

  Optional<CoachSalary> findByAcademyIdAndId(Long academyId, Long id);

  Page<CoachSalary> findByAcademyIdAndCoachId(Long academyId, Long coachId, Pageable pageable);

  Optional<CoachSalary> findByAcademyIdAndCoachIdAndYearAndMonth(
      Long academyId, Long coachId, Integer year, Integer month);
}

