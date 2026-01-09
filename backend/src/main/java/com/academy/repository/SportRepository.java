package com.academy.repository;

import com.academy.domain.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
  List<Sport> findByNameContainingIgnoreCase(String name);
}

