package com.academy.repository;

import com.academy.domain.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
  Page<Expense> findByAcademyId(Long academyId, Pageable pageable);

  Optional<Expense> findByAcademyIdAndId(Long academyId, Long id);

  Page<Expense> findByAcademyIdAndTrainingCentreId(
      Long academyId, Long trainingCentreId, Pageable pageable);
}

