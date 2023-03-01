package com.example.tuwaiqproject.Controller;

import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Model.Parking;
import com.example.tuwaiqproject.Service.ParkingService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/stadium/parkings")
@AllArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping("")
    public ResponseEntity getParkings(@AuthenticationPrincipal MyUser myUser){
        List<Parking> parkings= parkingService.getParkings(myUser);

        return ResponseEntity.status(200).body(parkings);
    }

    @GetMapping("/{id}")
    public ResponseEntity getParking(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id){
        Parking parking= parkingService.getParking(myUser,id);

        return ResponseEntity.status(200).body(parking);
    }

    @PostMapping("")
    public ResponseEntity addParking(@AuthenticationPrincipal MyUser myUser, @Valid @RequestBody ObjectNode objectNode){
        parkingService.addParking(myUser,objectNode);
        return ResponseEntity.status(201).body("Parking has been added Successfully");
    }


    @PutMapping("/{id}")
    public ResponseEntity updateParking(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id, @Valid @RequestBody Parking parking){

        parkingService.updateParking(myUser,id,parking);
        return ResponseEntity.status(200).body("Parking has been updated Successfully");
    }


    @DeleteMapping ("/{id}")
    public ResponseEntity deleteParking(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id){
        parkingService.deleteParking(myUser,id);
        return ResponseEntity.status(200).body("Parking has been deleted Successfully");
    }


}
