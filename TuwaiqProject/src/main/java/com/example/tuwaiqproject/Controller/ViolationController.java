package com.example.tuwaiqproject.Controller;

import com.example.tuwaiqproject.Model.FanViolation;
import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Model.Stadium;
import com.example.tuwaiqproject.Model.Violation;
import com.example.tuwaiqproject.Service.ViolationService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/violations")
public class ViolationController {
private  final ViolationService violationService;

    @GetMapping("")
    public ResponseEntity getAllViolation(){
        List<Violation> violationList=violationService.getAllViolation();
        return ResponseEntity.status(200).body(violationList);
    }

    @GetMapping("/{id}")
    public ResponseEntity getViolation(@PathVariable Integer id){
        Violation violation= violationService.getViolation(id);

        return ResponseEntity.status(200).body(violation);
    }

    @PostMapping("")
    public ResponseEntity addViolation(@Valid @RequestBody ObjectNode objectNode){
        violationService.addViolation(objectNode);
        return ResponseEntity.status(201).body("Added successfully !");
    }


    @PutMapping("/{id}")
    public ResponseEntity updateViolation(@Valid @PathVariable Integer id, @RequestBody Violation violation){
        violationService.updateViolation(id,violation);
        return ResponseEntity.status(200).body("updated successfully !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeViolation(@PathVariable Integer id){
        violationService.removeViolation(id);
        return ResponseEntity.status(200).body("deleted successfully !");
    }





}
