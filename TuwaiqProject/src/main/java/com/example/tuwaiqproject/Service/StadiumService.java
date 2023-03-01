package com.example.tuwaiqproject.Service;

import com.example.tuwaiqproject.Exception.ApiException;
import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Model.Stadium;
import com.example.tuwaiqproject.Model.StadiumAdmin;
import com.example.tuwaiqproject.Repository.StadiumRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StadiumService {
    private  final StadiumRepository stadiumRepository;

     public List<Stadium> getAllStadium(){
         return stadiumRepository.findAll();
     }

     public Stadium getStadium(Integer id){
        Stadium stadium = stadiumRepository.findStadiumById(id);
         if(stadium ==null){
             throw new ApiException("Stadium not found");
         }
         return stadium;
     }

public void addStadium(ObjectNode objectNode){
    String name = objectNode.get("name").asText();
    Integer totalSeatsNumber = objectNode.get("totalSeatsNumber").asInt();
    String city = objectNode.get("city").asText();
    Double latitude = objectNode.get("latitude").asDouble();
    Double longitude = objectNode.get("longitude").asDouble();

    Stadium stadium = new Stadium(null,name,totalSeatsNumber,city,latitude,longitude,null,null,null,null);
    stadiumRepository.save(stadium);
}

     public void updateStadium(Integer id ,Stadium stadium){
         Stadium currentStadium= stadiumRepository.findStadiumById(id);
         if(currentStadium==null){
             throw new ApiException("Stadium not found");
         }
         StadiumAdmin stadiumAdmin= currentStadium.getStadiumAdmin();
         stadium.setId(currentStadium.getId());
         stadium.setStadiumAdmin(stadiumAdmin);
         stadiumRepository.save(stadium);

     }
     public void  removeStadium(Integer id){
         Stadium stadium1=stadiumRepository.findStadiumById(id);
         if(stadium1==null){
             throw new ApiException("Stadium not found");
         }
         stadiumRepository.deleteById(id);
     }


}
