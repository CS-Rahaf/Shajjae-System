package com.example.tuwaiqproject.ServiceTest;


import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Model.Violation;
import com.example.tuwaiqproject.Repository.ViolationRepository;
import com.example.tuwaiqproject.Service.ViolationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ViolationServiceTest {


    @InjectMocks
    ViolationService violationService;

    @Mock
    ViolationRepository violationRepository;

    MyUser myUser;
    Violation violation;
    List<Violation> violations = new ArrayList<>();
    final ObjectMapper mapper = new ObjectMapper();
    final ObjectNode userObject = mapper.createObjectNode();

    @BeforeEach
    void setUp() {

        myUser= new MyUser(null,"RahafTest","09090","SUPER",null,null);

        violation = new Violation(null,"TestViolation212",100,null);


        userObject.set("name", mapper.convertValue("TestViolation212", JsonNode.class));
        userObject.set("price", mapper.convertValue(100, JsonNode.class));

        violations.add(violation);
    }

    @Test
    void getUserByIdTest(){
        when(violationRepository.findViolationById(violation.getId())).thenReturn(violation);

        Violation violation1 = violationService.getViolation(violation.getId());

        Assertions.assertEquals(violation,violation1);
        verify(violationRepository,times(1)).findViolationById(violation.getId());
    }

    @Test
    void getAllViolationTest(){
        when(violationRepository.findAll()).thenReturn(violations);

        List<Violation> violationList =violationService.getAllViolation();

        Assertions.assertEquals(violationList,violations);
        verify(violationRepository,times(1)).findAll();
    }

    @Test
    void addViolationTest(){

        violationService.addViolation(userObject);
        verify(violationRepository,times(1)).save(violation);

    }



}
