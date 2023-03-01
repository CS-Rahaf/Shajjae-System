package com.example.tuwaiqproject.Controller;


import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Model.Parking;
import com.example.tuwaiqproject.Service.MyUserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final MyUserService myUserService;


    @PostMapping("/register")
    public ResponseEntity register(@Valid @RequestBody ObjectNode objectNode){
        myUserService.register(objectNode);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been registered successfully!");
    }


    @PostMapping("/admin/register")
    public ResponseEntity adminRegister(@Valid @RequestBody ObjectNode objectNode){
        myUserService.stadiumAdminRegister(objectNode);
        return ResponseEntity.status(HttpStatus.CREATED).body("Stadium admin has been registered successfully! ");
    }


    @PostMapping("/login")
    public ResponseEntity login(){

        return ResponseEntity.status(HttpStatus.OK).body("Welcome back");
    }


}
