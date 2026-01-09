package com.academy.repository;

import com.academy.domain.TrainingProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {
  Page<TrainingProgram> findByAcademyId(Long academyId, Pageable pageable);

  Optional<TrainingProgram> findByAcademyIdAndId(Long academyId, Long id);
}

