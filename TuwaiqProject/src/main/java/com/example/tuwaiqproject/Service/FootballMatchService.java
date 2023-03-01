package com.example.tuwaiqproject.Service;

import com.example.tuwaiqproject.Exception.ApiException;
import com.example.tuwaiqproject.Model.*;
import com.example.tuwaiqproject.Repository.FootballMatchRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class FootballMatchService {
    
    private final FootballMatchRepository footballMatchRepository;
    private final StadiumService stadiumService;
    private final MyUserService myUserService;


    public List<FootballMatch> getMatches(){

        return footballMatchRepository.findAll();
    }

    public FootballMatch getMatch(Integer id){
        FootballMatch footballMatch = footballMatchRepository.findFootballMatchById(id);
        if(footballMatch ==null){
            throw new ApiException("Match not found");
        }
        return footballMatch;
    }

    public List<FootballMatch> getAllStadiumMatches(MyUser myUser){
        MyUser myUser1= myUserService.getUser(myUser.getId());
        return myUser1.getStadiumAdmin().getStadium().getFootballMatches();
    }


    public FootballMatch getStadiumMatch(MyUser myUser, Integer id){
        FootballMatch footballMatch = footballMatchRepository.findFootballMatchById(id);
        MyUser myUser1= myUserService.getUser(myUser.getId());

        if(myUser1.getStadiumAdmin().getStadium().getId() != footballMatch.getStadium().getId()){
            throw new ApiException("Please enter right seat id");
        }

        if(footballMatch ==null){
            throw new ApiException("Match not found");
        }
        return footballMatch;
    }
    public void addMatch(MyUser myUser, ObjectNode objectNode){
        Integer stadiumId = objectNode.get("stadiumId").asInt();
        String firstClub = objectNode.get("firstClub").asText();
        String secondClub = objectNode.get("secondClub").asText();
        Double vipPrice = objectNode.get("vipPrice").asDouble();
        Double classicPrice = objectNode.get("classicPrice").asDouble();

        ObjectMapper mapper = new ObjectMapper().
                registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        LocalTime matchTime= mapper.convertValue(objectNode.get("matchTime"), LocalTime.class);;
        LocalDate matchDate = mapper.convertValue(objectNode.get("matchDate"), LocalDate.class);

        MyUser myUser1= myUserService.getUser(myUser.getId());
        StadiumAdmin stadiumAdmin = myUser1.getStadiumAdmin();
        Stadium stadium = stadiumService.getStadium(stadiumId);

        if(stadiumAdmin.getStadium().getId() != stadium.getId()){
            throw new ApiException("Please enter right stadium id");
        }

        FootballMatch footballMatch = new FootballMatch(null,firstClub,secondClub,matchDate,matchTime,vipPrice,classicPrice,stadium,null);
        footballMatchRepository.save(footballMatch);
    }

    public void updateMatch(MyUser myUser, Integer id, FootballMatch footballMatch){
        FootballMatch currentFootballMatch = getStadiumMatch(myUser,id);
        MyUser myUser1= myUserService.getUser(myUser.getId());
        StadiumAdmin stadiumAdmin = myUser1.getStadiumAdmin();
        Stadium stadium = stadiumAdmin.getStadium();

        if(stadium.getId() != currentFootballMatch.getStadium().getId()){
            throw new ApiException("Please enter right stadium id");
        }

        if(currentFootballMatch ==null){
            throw new ApiException("Match not found");
        }

        footballMatch.setId(currentFootballMatch.getId());
        footballMatch.setStadium(stadium);
        footballMatchRepository.save(footballMatch);
    }

    public void deleteMatch(MyUser myUser, Integer id){
        FootballMatch currentFootballMatch = footballMatchRepository.findFootballMatchById(id);
        MyUser myUser1= myUserService.getUser(myUser.getId());

        if(myUser1.getStadiumAdmin().getStadium().getId() != currentFootballMatch.getStadium().getId()){
            throw new ApiException("Please enter right seat id");
        }

        if(currentFootballMatch == null){
            throw new ApiException("Match not found");
        }
        footballMatchRepository.deleteById(id);
    }


    public double ComputeOccupiedSeatsPercentage(MyUser myUser, Integer matchId){
        FootballMatch footballMatch0 = getMatch(matchId);
        if(footballMatch0 == null){
            throw new ApiException("Enter valid match id");
        }

        FootballMatch footballMatch = getStadiumMatch(myUser,matchId);

        StadiumAdmin stadiumAdmin = myUserService.getUser(myUser.getId()).getStadiumAdmin();
        Stadium stadium= stadiumAdmin.getStadium();

        int totalStadiumsSeats = stadium.getTotalSeatsNumber();
        int occupiedSeats = footballMatch.getBookings().size();

        double percentage= (occupiedSeats *100.0)/totalStadiumsSeats;
        return percentage;
    }


  /*  public  FootballMatch getMatchByName(String firstClub ,String SecondClub){
        FootballMatch footballMatch=footballMatchRepository.findFootballMatchByFirstClubOrSecondClub( firstClub , SecondClub);
        if(footballMatch ==null){
            throw new ApiException("Match not found");
        }
        return footballMatch;
    }
    public  FootballMatch getMatchByClubName(String firstClub){
    FootballMatch footballMatch=footballMatchRepository.findFootballMatchByFirstClub(firstClub);
    if(footballMatch ==null){
        throw new ApiException("Match not found");
    }
    return footballMatch;

}
*/

public  List<FootballMatch> getAllMatchesByClubName(String club){
        List<FootballMatch> footballMatches= footballMatchRepository.findAllByFirstClubOrSecondClub(club,club);

        return footballMatches;
}


}
