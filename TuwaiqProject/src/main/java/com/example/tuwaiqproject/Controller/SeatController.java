package com.example.tuwaiqproject.Controller;

import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Model.Seat;
import com.example.tuwaiqproject.Service.SeatService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stadium/seats")
public class SeatController {
    private final SeatService seatService;


    @GetMapping("")
    public ResponseEntity getAllSeat(@AuthenticationPrincipal MyUser myUser){
        List<Seat> seatList= seatService.getAllSeat(myUser);
        return ResponseEntity.status(200).body(seatList);

    }

    @GetMapping("/{id}")
    public ResponseEntity getSeats(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id){
        Seat seat= seatService.getSeatById(myUser, id);

        return ResponseEntity.status(200).body(seat);
    }

    @PostMapping("")
    public ResponseEntity addSeat(@AuthenticationPrincipal MyUser myUser, @Valid @RequestBody ObjectNode objectNode){
        seatService.addSeat(myUser, objectNode);
        return ResponseEntity.status(200).body("Added successfully !");

    }
    @PutMapping("/{id}")
    public ResponseEntity updateSeat(@AuthenticationPrincipal MyUser myUser,@Valid @PathVariable Integer id, @RequestBody Seat seat){
        seatService.updateSeat(myUser,id,seat);
        return ResponseEntity.status(200).body("updated successfully !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeSeat(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id){
        seatService.removeSeat(myUser,id);
        return ResponseEntity.status(200).body("deleted successfully !");

    }


}
