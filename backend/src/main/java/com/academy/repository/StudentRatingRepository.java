package com.academy.repository;

import com.academy.domain.StudentRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRatingRepository extends JpaRepository<StudentRating, Long> {
  Page<StudentRating> findByAcademyId(Long academyId, Pageable pageable);

  Optional<StudentRating> findByAcademyIdAndId(Long academyId, Long id);

  Page<StudentRating> findByAcademyIdAndStudentId(Long academyId, Long studentId, Pageable pageable);

  Page<StudentRating> findByAcademyIdAndStudentIdAndSportId(
      Long academyId, Long studentId, Long sportId, Pageable pageable);

  Page<StudentRating> findByAcademyIdAndStudentIdAndSportIdOrderByRatingDateDesc(
      Long academyId, Long studentId, Long sportId, Pageable pageable);
}

