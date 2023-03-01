package com.example.tuwaiqproject.Service;

import com.example.tuwaiqproject.Exception.ApiException;
import com.example.tuwaiqproject.Model.*;
import com.example.tuwaiqproject.Repository.ParkingRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ParkingService {

    private final ParkingRepository parkingRepository;
    private final MyUserService myUserService;
    private final StadiumService stadiumService;


    public List<Parking> getParkings(MyUser myUser){
        MyUser myUser1= myUserService.getUser(myUser.getId());
        return myUser1.getStadiumAdmin().getStadium().getParkings();
    }


    public Parking getParking(MyUser myUser, Integer id){
        Parking parking = parkingRepository.findParkingById(id);
        MyUser myUser1= myUserService.getUser(myUser.getId());

        if(myUser1.getStadiumAdmin().getStadium().getId() != parking.getStadium().getId()){
            throw new ApiException("Please enter right seat id");
        }

        if(parking==null){
            throw new ApiException("Parking not found");
        }
        return parking;
    }
    public void addParking(MyUser myUser, ObjectNode objectNode){
        Integer stadiumId = objectNode.get("stadiumId").asInt();
        Integer spotNumber = objectNode.get("spotNumber").asInt();
        Integer floor = objectNode.get("floor").asInt();

        MyUser myUser1= myUserService.getUser(myUser.getId());
        StadiumAdmin stadiumAdmin = myUser1.getStadiumAdmin();
        Stadium stadium = stadiumService.getStadium(stadiumId);

        if(stadiumAdmin.getStadium().getId() != stadium.getId()){
            throw new ApiException("Please enter right stadium id");
        }

        Parking parking = new Parking(null,spotNumber,floor,stadium,null);
        parkingRepository.save(parking);

    }

    public void updateParking(MyUser myUser, Integer id, Parking parking){
        Parking currentParking =getParking(myUser,id);
        MyUser myUser1= myUserService.getUser(myUser.getId());
        Stadium stadium = myUser1.getStadiumAdmin().getStadium();

        if(myUser1.getStadiumAdmin().getStadium().getId() != currentParking.getStadium().getId()){
            throw new ApiException("Please enter right stadium id");
        }

        if(currentParking==null){
            throw new ApiException("Parking not found");
        }
        parking.setId(currentParking.getId());
        parking.setStadium(stadium);
        parkingRepository.save(parking);
    }

    public void deleteParking(MyUser myUser, Integer id){
        Parking currentParking = parkingRepository.findParkingById(id);
        MyUser myUser1= myUserService.getUser(myUser.getId());

        if(myUser1.getStadiumAdmin().getStadium().getId() != currentParking.getStadium().getId()){
            throw new ApiException("Please enter right seat id");
        }

        if(currentParking == null){
            throw new ApiException("Parking not found");
        }
        parkingRepository.deleteById(id);
    }


    public Parking findParkingBySpotNumber(int spotNumber){
        Parking parking= parkingRepository.findParkingBySpotNumber(spotNumber);
        if(parking==null){
            throw new ApiException("Parking not found");
        }
        return parking;
    }


}
