package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository< Booking, Integer> {

    Booking findBookingById(Integer id);

    List<Booking> findAllBookingByFanUsers(Integer fanUserId);

}
