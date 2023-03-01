package com.example.tuwaiqproject.ControllerTest;

import com.example.tuwaiqproject.Controller.StadiumController;
import com.example.tuwaiqproject.Model.Stadium;
import com.example.tuwaiqproject.Service.StadiumService;
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
@WebMvcTest(value = StadiumController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class StadiumControllerTest {

    @MockBean
    StadiumService stadiumService;

    @Autowired
    MockMvc mockMvc;

    Stadium stadium;
    List<Stadium> stadiums= new ArrayList<>();

    final ObjectMapper mapper = new ObjectMapper();
    final ObjectNode userObject = mapper.createObjectNode();

    @BeforeEach
    void setUp() {

       stadium = new Stadium(null,"StadiumTest",100,"city",19,19,null,null,null,null);
       stadiums.add(stadium);

        userObject.set("name", mapper.convertValue("StadiumTest", JsonNode.class));
        userObject.set("totalSeatsNumber", mapper.convertValue(100, JsonNode.class));
        userObject.set("city", mapper.convertValue("Medina", JsonNode.class));
        userObject.set("latitude", mapper.convertValue(10, JsonNode.class));
        userObject.set("longitude", mapper.convertValue(10, JsonNode.class));

    }

    @Test
    public void GetAllStadiums() throws Exception {
        Mockito.when(stadiumService.getAllStadium()).thenReturn(stadiums);

        mockMvc.perform(get("/api/v1/admin/stadiums"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("StadiumTest"));
    }

    @Test
    public void addStadiumsTest() throws  Exception {
        mockMvc.perform(post("/api/v1/admin/stadiums")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(userObject)))
                .andExpect(status().isCreated());
    }

}
