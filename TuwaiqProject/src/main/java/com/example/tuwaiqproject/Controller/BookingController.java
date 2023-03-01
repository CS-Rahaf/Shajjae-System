package com.example.tuwaiqproject.Controller;

import com.example.tuwaiqproject.Model.Booking;
import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Service.BookingService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("")
    public ResponseEntity getBookings(){
        List<Booking> Bookings= bookingService.getBookings();

        return ResponseEntity.status(200).body(Bookings);
    }

    @GetMapping("/{id}")
    public ResponseEntity getBooking(@PathVariable Integer id){
        Booking Booking= bookingService.getBooking(id);

        return ResponseEntity.status(200).body(Booking);
    }



}
