package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat ,Integer> {
    Seat findSeatById(Integer id);
    Seat findSeatBySeatNumber(int seatNumber);
}
