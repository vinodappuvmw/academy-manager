package com.academy.repository;

import com.academy.domain.RatingTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RatingTemplateRepository extends JpaRepository<RatingTemplate, Long> {
  Page<RatingTemplate> findByAcademyId(Long academyId, Pageable pageable);

  Optional<RatingTemplate> findByAcademyIdAndId(Long academyId, Long id);

  List<RatingTemplate> findByAcademyIdAndIsActiveTrue(Long academyId);
}

