package com.example.tuwaiqproject.Controller;


import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Model.Stadium;
import com.example.tuwaiqproject.Service.StadiumService;
import com.example.tuwaiqproject.Service.ViolationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/stadium")
@AllArgsConstructor
public class StadiumAdminController {

    private final StadiumService stadiumService;
    private ViolationService violationService;

    @GetMapping("/stadiums/{id}")
    public ResponseEntity getStadium(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id){
        Stadium stadium= stadiumService.getStadium(id);

        return ResponseEntity.status(200).body(stadium);
    }

    @PostMapping("/violations/assign")
    public ResponseEntity assignViolationToUser(@Valid @RequestBody ObjectNode objectNode){
        violationService.assignToUser(objectNode);
        return ResponseEntity.status(200).body("assign successfully!");
    }


}
