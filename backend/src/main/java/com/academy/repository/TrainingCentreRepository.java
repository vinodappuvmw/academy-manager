package com.academy.repository;

import com.academy.domain.TrainingCentre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TrainingCentreRepository extends JpaRepository<TrainingCentre, Long> {
  Page<TrainingCentre> findByAcademyId(Long academyId, Pageable pageable);

  Optional<TrainingCentre> findByAcademyIdAndId(Long academyId, Long id);
}

