package com.example.tuwaiqproject.ServiceTest;


import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Repository.MyUserRepository;
import com.example.tuwaiqproject.Service.MyUserService;
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

@ExtendWith(MockitoExtension.class)
public class MyUserServiceTest {

    @InjectMocks
    MyUserService myUserService;

    @Mock
    MyUserRepository myUserRepository;

    MyUser myUser;
    List<MyUser> myUserList = new ArrayList<>();
    @BeforeEach
    void setUp() {

        myUser= new MyUser(null,"RahafTest","09090","FAN",null,null);

        myUserList.add(myUser);
    }

    @Test
    void getUserByIdTest(){
        when(myUserRepository.findMyUsersById(myUser.getId())).thenReturn(myUser);

        MyUser myUser1 =myUserService.getUser(myUser.getId());

        Assertions.assertEquals(myUser,myUser1);
        verify(myUserRepository,times(1)).findMyUsersById(myUser.getId());
    }

    @Test
    void getAllUsersTest(){
        when(myUserRepository.findAll()).thenReturn(myUserList);

        List<MyUser> myUsers =myUserService.getUsers();

        Assertions.assertEquals(myUserList.size(),myUsers.size());
        verify(myUserRepository,times(1)).findAll();
    }

}
