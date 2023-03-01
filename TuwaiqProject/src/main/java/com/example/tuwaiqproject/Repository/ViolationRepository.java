package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.Violation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViolationRepository  extends JpaRepository<Violation,Integer> {
    Violation findViolationById(Integer id);

}
