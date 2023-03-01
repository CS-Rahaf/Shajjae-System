package com.example.tuwaiqproject.Service;


import com.example.tuwaiqproject.Exception.ApiException;
import com.example.tuwaiqproject.Model.*;
import com.example.tuwaiqproject.Repository.FanUserRepository;
import com.example.tuwaiqproject.Repository.MyUserRepository;
import com.example.tuwaiqproject.Repository.StadiumRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MyUserService {

    private final MyUserRepository myUserRepository;
    private final FanUserRepository fanUserRepository;
    private final StadiumRepository stadiumRepository;
    private final StadiumService stadiumService;


    public void register(ObjectNode objectNode) {
        String username = objectNode.get("username").asText();
        String password = objectNode.get("password").asText();
        String name = objectNode.get("name").asText();
        String phoneNumber = objectNode.get("phoneNumber").asText();
        String gender = objectNode.get("gender").asText();
        boolean disability = objectNode.get("disability").asBoolean();

        MyUser myUser = new MyUser(null,username,password,"FAN",null,null);

        String hashedPassword=new BCryptPasswordEncoder().encode(myUser.getPassword());

        myUser.setPassword(hashedPassword);

        FanUser fanUser = new FanUser(null,name,phoneNumber,gender,disability,false,myUser,null,null);
        myUser.setFanUser(fanUser);
        myUserRepository.save(myUser);
    }

    //This endpoint just for the admin
    public List<MyUser> getUsers(){

        return myUserRepository.findAll();
    }

    //This endpoint just for the admin
    public MyUser getUser(Integer id){
        MyUser myUser = myUserRepository.findMyUsersById(id);
        if(myUser==null){
            throw new ApiException("User not found");
        }
        return myUser;
    }


    public void stadiumAdminRegister(ObjectNode objectNode) {
        Integer stadiumId = objectNode.get("stadiumId").asInt();
        String username = objectNode.get("username").asText();
        String password = objectNode.get("password").asText();

        Stadium stadium = stadiumService.getStadium(stadiumId);

        MyUser myUser = new MyUser(null,username,password,"STADIUM",null,null);

        String hashedPassword=new BCryptPasswordEncoder().encode(myUser.getPassword());

        myUser.setPassword(hashedPassword);

        StadiumAdmin stadiumAdmin = new StadiumAdmin(null,myUser,stadium);

        myUser.setStadiumAdmin(stadiumAdmin);
        myUserRepository.save(myUser);
        stadium.setStadiumAdmin(stadiumAdmin);
        stadiumRepository.save(stadium);

    }
}
