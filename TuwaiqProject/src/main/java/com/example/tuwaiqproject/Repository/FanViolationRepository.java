package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.FanViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FanViolationRepository extends JpaRepository<FanViolation, Integer> {

    FanViolation findFanViolationById(Integer id);
}
