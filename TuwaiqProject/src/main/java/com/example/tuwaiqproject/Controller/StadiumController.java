package com.example.tuwaiqproject.Controller;

import com.example.tuwaiqproject.Model.Stadium;
import com.example.tuwaiqproject.Service.StadiumService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/admin/stadiums")
public class StadiumController {

    private final StadiumService stadiumService;
    @GetMapping("")
      public ResponseEntity getAllStadium(){
        List<Stadium> stadiumList= stadiumService.getAllStadium();
        return ResponseEntity.status(200).body(stadiumList);

      }

    @GetMapping("/{id}")
    public ResponseEntity getStadium(@PathVariable Integer id){
        Stadium stadium= stadiumService.getStadium(id);
        return ResponseEntity.status(200).body(stadium);

    }

      @PostMapping("")
      public ResponseEntity addStadium(@Valid @RequestBody ObjectNode objectNode){
        stadiumService.addStadium(objectNode);
        return ResponseEntity.status(201).body("Added successfully !");

      }

      @PutMapping("/{id}")
    public ResponseEntity updateStadium(@Valid @PathVariable Integer id, @RequestBody Stadium stadium){
        stadiumService.updateStadium(id,stadium);
          return ResponseEntity.status(200).body("updated successfully !");
      }

      @DeleteMapping("/{id}")
     public ResponseEntity removeStadium(@PathVariable Integer id){
         stadiumService.removeStadium(id);
         return ResponseEntity.status(200).body("deleted successfully !");
     }



}
