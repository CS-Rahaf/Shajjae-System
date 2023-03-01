package com.example.tuwaiqproject.ControllerTest;


import com.example.tuwaiqproject.Controller.ViolationController;
import com.example.tuwaiqproject.Model.Violation;
import com.example.tuwaiqproject.Service.ViolationService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ViolationController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ViolationControllerTest {

    @MockBean
    ViolationService violationService;

    @Autowired
    MockMvc mockMvc;

    Violation violation;
    List<Violation> violations= new ArrayList<>();

    final ObjectMapper mapper = new ObjectMapper();
    final ObjectNode userObject = mapper.createObjectNode();

    @BeforeEach
    void setUp() {

        violation= new Violation(1,"ViolationTest55",100,null);
        violations.add(violation);

        userObject.set("name", mapper.convertValue("ViolationTest66", JsonNode.class));
        userObject.set("price", mapper.convertValue(100, JsonNode.class));

    }

    @Test
    public void getAllViolationsTest() throws Exception {
        Mockito.when(violationService.getAllViolation()).thenReturn(violations);

        mockMvc.perform(get("/api/v1/admin/violations"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("ViolationTest55"));
    }

    @Test
    public void addViolationTest() throws  Exception {
        mockMvc.perform(post("/api/v1/admin/violations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(userObject)))
                .andExpect(status().isCreated());
    }

    @Test
    public void deleteViolationTest() throws Exception{
        mockMvc.perform(delete("/api/v1/admin/violations/{id}",violation.getId()))
                .andExpect(status().isOk());
    }



}
