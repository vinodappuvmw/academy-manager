package com.academy.repository;

import com.academy.domain.Coach;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
  Page<Coach> findByAcademyId(Long academyId, Pageable pageable);

  Optional<Coach> findByAcademyIdAndId(Long academyId, Long id);
}

