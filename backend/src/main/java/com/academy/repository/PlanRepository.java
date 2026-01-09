package com.academy.repository;

import com.academy.domain.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
  Page<Plan> findByAcademyId(Long academyId, Pageable pageable);

  Optional<Plan> findByAcademyIdAndId(Long academyId, Long id);
}

