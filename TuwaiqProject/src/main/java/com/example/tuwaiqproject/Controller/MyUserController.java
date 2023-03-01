package com.example.tuwaiqproject.Controller;


import com.example.tuwaiqproject.Model.*;
import com.example.tuwaiqproject.Service.BookingService;
import com.example.tuwaiqproject.Service.FootballMatchService;
import com.example.tuwaiqproject.Service.MyUserService;
import com.example.tuwaiqproject.Service.ViolationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/myuser")
@AllArgsConstructor
public class MyUserController {

    private final MyUserService myUserService;
    private final BookingService bookingService;
    private final ViolationService violationService;
    private final FootballMatchService footballMatchService;

    //Those endpoints are just for the admin
/*
    @GetMapping("")
    public ResponseEntity getUsers(){
        List<MyUser> myUsers= myUserService.getUsers();

        return ResponseEntity.status(200).body(myUsers);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Integer id){
       MyUser myUser= myUserService.getUser(id);

        return ResponseEntity.status(200).body(myUser);
    }*/

    @GetMapping("/matches")
    public ResponseEntity getMatches(){
        List<FootballMatch> footballMatches = footballMatchService.getMatches();

        return ResponseEntity.status(200).body(footballMatches);
    }

    @PostMapping("/bookings")
    public ResponseEntity addBooking(@AuthenticationPrincipal MyUser myUser, @Valid @RequestBody ObjectNode objectNode){
        Booking booking= bookingService.addBooking(myUser, objectNode , false);
        return ResponseEntity.status(200).body(booking);
    }

    @PostMapping("/bookings/gift")
    public ResponseEntity giftBooking(@AuthenticationPrincipal MyUser myUser, @Valid @RequestBody ObjectNode objectNode){
        Booking booking= bookingService.giftBooking(myUser, objectNode);
        return ResponseEntity.status(200).body(booking);
    }

    @GetMapping("/bookings/history")
    public ResponseEntity getUserBookingHistory(@AuthenticationPrincipal MyUser myUser){
        List<Booking> bookings= bookingService.getUserBookings(myUser);
        return ResponseEntity.status(200).body(bookings);
    }

    @PostMapping("/bookings/{bookingId}/payment")
    public ResponseEntity payForBooking(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer bookingId, @Valid @RequestBody ObjectNode objectNode){
        bookingService.payForBooking(myUser, bookingId, objectNode);
        return ResponseEntity.status(200).body("Booking payment is done Successfully");
    }

    @GetMapping("/violations/history")
    public ResponseEntity getAllUserViolation(@AuthenticationPrincipal MyUser myUser){
        List<FanViolation> fanViolations=violationService.getAllUserViolations(myUser.getId());
        return ResponseEntity.status(200).body(fanViolations);
    }

    @PostMapping("/violations/{fanViolationId}/payment")
    public ResponseEntity payForValidation(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer fanViolationId, @RequestBody ObjectNode objectNode){
        violationService.payForValidation(myUser,fanViolationId, objectNode);
        return ResponseEntity.status(200).body("Violation payment is done successfully!");
    }

    @GetMapping("/bookings/gift/{giftId}")
    public ResponseEntity giftReception(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer giftId){
        bookingService.giftReception(myUser,giftId);
        return ResponseEntity.status(200).body("You got it!");
    }

    @PostMapping("/bookings/gift/{giftId}/payment")
    public ResponseEntity payForGift(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer giftId, @Valid @RequestBody ObjectNode objectNode){
        bookingService.payForGift(myUser, giftId, objectNode);
        return ResponseEntity.status(200).body("Gift booking payment is done Successfully");
    }

    @GetMapping("/matches/club/{clubName}")
    public ResponseEntity getMatchesByClub(@AuthenticationPrincipal MyUser myUser, @PathVariable String clubName){
        List<FootballMatch> footballMatches= footballMatchService.getAllMatchesByClubName(clubName);
        return ResponseEntity.status(200).body(footballMatches);
    }

}
