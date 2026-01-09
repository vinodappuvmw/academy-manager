package com.academy.repository;

import com.academy.domain.HomeWorkout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HomeWorkoutRepository extends JpaRepository<HomeWorkout, Long> {
  Page<HomeWorkout> findByAcademyId(Long academyId, Pageable pageable);

  Optional<HomeWorkout> findByAcademyIdAndId(Long academyId, Long id);

  Page<HomeWorkout> findByAcademyIdAndStudentId(Long academyId, Long studentId, Pageable pageable);

  Page<HomeWorkout> findByAcademyIdAndCoachId(Long academyId, Long coachId, Pageable pageable);

  Page<HomeWorkout> findByAcademyIdAndSportId(Long academyId, Long sportId, Pageable pageable);

  Page<HomeWorkout> findByAcademyIdAndStudentIdAndSportId(
      Long academyId, Long studentId, Long sportId, Pageable pageable);

  Page<HomeWorkout> findByAcademyIdAndCoachIdAndSportId(
      Long academyId, Long coachId, Long sportId, Pageable pageable);
}

