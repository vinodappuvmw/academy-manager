package com.academy.repository;

import com.academy.domain.Periodisation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PeriodisationRepository extends JpaRepository<Periodisation, Long> {
  Page<Periodisation> findByAcademyId(Long academyId, Pageable pageable);

  Optional<Periodisation> findByAcademyIdAndId(Long academyId, Long id);

  Page<Periodisation> findByAcademyIdAndStudentId(Long academyId, Long studentId, Pageable pageable);

  Page<Periodisation> findByAcademyIdAndTrainingProgramId(
      Long academyId, Long trainingProgramId, Pageable pageable);
}

