package com.academy.repository;

import com.academy.domain.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
  Page<Student> findByAcademyId(Long academyId, Pageable pageable);

  Optional<Student> findByAcademyIdAndId(Long academyId, Long id);
}

