package com.academy.repository;

import com.academy.domain.TrainingSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
  Page<TrainingSession> findByAcademyId(Long academyId, Pageable pageable);

  Optional<TrainingSession> findByAcademyIdAndId(Long academyId, Long id);

  Page<TrainingSession> findByAcademyIdAndDateBetween(
      Long academyId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}

