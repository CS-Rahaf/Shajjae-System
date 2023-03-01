package com.example.tuwaiqproject.Controller;

import com.example.tuwaiqproject.Model.FootballMatch;
import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Service.FootballMatchService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/stadium/matches")
@AllArgsConstructor
public class FootballMatchController {
    
    private final FootballMatchService footballMatchService;

    @GetMapping("")
    public ResponseEntity getMatches(@AuthenticationPrincipal MyUser myUser){
        List<FootballMatch> footballMatches = footballMatchService.getAllStadiumMatches(myUser);

        return ResponseEntity.status(200).body(footballMatches);
    }

    @GetMapping("/{id}")
    public ResponseEntity getMatch(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id){
        FootballMatch footballMatch = footballMatchService.getStadiumMatch(myUser, id);

        return ResponseEntity.status(200).body(footballMatch);
    }

    @PostMapping("")
    public ResponseEntity addMatch(@AuthenticationPrincipal MyUser myUser, @Valid @RequestBody ObjectNode objectNode){
        footballMatchService.addMatch(myUser, objectNode);
        return ResponseEntity.status(200).body("Match has been added Successfully");
    }


    @PutMapping("/{id}")
    public ResponseEntity updateMatch(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id, @Valid @RequestBody FootballMatch footballMatch){

        footballMatchService.updateMatch(myUser, id, footballMatch);
        return ResponseEntity.status(200).body("Match has been updated Successfully");
    }


    @DeleteMapping ("/{id}")
    public ResponseEntity deleteMatch(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer id){
        footballMatchService.deleteMatch(myUser, id);
        return ResponseEntity.status(200).body("Match has been deleted Successfully");
    }

    @GetMapping("/{matchId}/seats/occupation")
    public ResponseEntity getOccupiedSeats(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer matchId ){
        double percentage =footballMatchService.ComputeOccupiedSeatsPercentage(myUser,matchId);
        return ResponseEntity.status(200).body(percentage);
    }


}
