package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium,Integer> {
Stadium findStadiumById(Integer id);
}
