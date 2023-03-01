package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.Parking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Integer> {

    Parking findParkingById(Integer id);

    Parking findParkingBySpotNumber(int spotNumber);

}
