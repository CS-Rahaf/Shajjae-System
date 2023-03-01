package com.example.tuwaiqproject.Service;


import com.example.tuwaiqproject.Exception.ApiException;
import com.example.tuwaiqproject.Model.*;
import com.example.tuwaiqproject.Repository.BookingRepository;
import com.example.tuwaiqproject.Repository.FanUserRepository;
import com.example.tuwaiqproject.Repository.SeatRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SeatService {
    private final SeatRepository seatRepository;
    private final StadiumService stadiumService;
    private final MyUserService myUserService;

    public List<Seat> getAllSeat(MyUser myUser){
        MyUser myUser1= myUserService.getUser(myUser.getId());
        return myUser1.getStadiumAdmin().getStadium().getSeats();
    }

    public Seat getSeatById(MyUser myUser, Integer id) {
        Seat seat = seatRepository.findSeatById(id);
        MyUser myUser1= myUserService.getUser(myUser.getId());
        Stadium stadium = myUser1.getStadiumAdmin().getStadium();

        if(stadium.getId() != seat.getStadium().getId()){
            throw new ApiException("Please enter right seat id");
        }

        if(seat ==null){
            throw new ApiException("Seat not found");
        }
        return seat;
    }

    public void addSeat(MyUser myUser, ObjectNode objectNode){
        Integer stadiumId = objectNode.get("stadiumId").asInt();
        String seatType = objectNode.get("seatType").asText();
        Integer seatNumber = objectNode.get("seatNumber").asInt();
        Integer seatGroup = objectNode.get("seatGroup").asInt();
        Integer seatRow = objectNode.get("seatRow").asInt();

        MyUser myUser1= myUserService.getUser(myUser.getId());
        StadiumAdmin stadiumAdmin = myUser1.getStadiumAdmin();
        Stadium stadiumForAdmin= stadiumAdmin.getStadium();
        Stadium stadium = stadiumService.getStadium(stadiumId);

        if(stadiumForAdmin.getId() != stadium.getId()){
            throw new ApiException("Please enter right stadium id");
        }

        Seat seat = new Seat(null,seatType,seatNumber,seatGroup,seatRow,stadium,null);
        seatRepository.save(seat);
    }
    public void updateSeat(MyUser myUser, Integer seatId , Seat seat){
        Seat currentSeat= getSeatById(myUser,seatId);
        MyUser myUser1= myUserService.getUser(myUser.getId());
        Stadium stadium = myUser1.getStadiumAdmin().getStadium();

        if(stadium.getId() != currentSeat.getStadium().getId()){
            throw new ApiException("Please enter right stadium id");
        }

        if(currentSeat==null){
            throw new ApiException("Seat not found");
        }

        seat.setId(currentSeat.getId());
        seat.setStadium(stadium);
        seatRepository.save(seat);
    }

    public void  removeSeat(MyUser myUser,Integer seatId){
        Seat seat1= seatRepository.findSeatById(seatId);
        MyUser myUser1= myUserService.getUser(myUser.getId());

        if(myUser1.getStadiumAdmin().getStadium().getId() != seat1.getStadium().getId()){
            throw new ApiException("Please enter right seat id");
        }

        if(seat1==null){
            throw new ApiException("Seat not found");
        }

        seatRepository.deleteById(seatId);
    }

    public Seat findSeatBySeatNumber(int seatNumber){
        Seat seat = seatRepository.findSeatBySeatNumber(seatNumber);
        if(seat==null){
            throw new ApiException("Seat not found");
        }
        return seat;
    }

}
