package com.example.tuwaiqproject.Service;

import com.example.tuwaiqproject.Exception.ApiException;
import com.example.tuwaiqproject.Model.*;
import com.example.tuwaiqproject.Repository.FanUserRepository;
import com.example.tuwaiqproject.Repository.FanViolationRepository;
import com.example.tuwaiqproject.Repository.MyUserRepository;
import com.example.tuwaiqproject.Repository.ViolationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ViolationService {
    private final ViolationRepository violationRepository;
    private final FanViolationRepository fanViolationRepository;
    public final MyUserRepository myUserRepository;
    private final MyUserService myUserService;
    private final FanUserRepository fanUserRepository;

    public List<Violation> getAllViolation(){

        return violationRepository.findAll();
    }

    //This function maybe should bed move to myUserService
    public List<FanViolation> getAllUserViolations(Integer myUserId){
        MyUser myUser = myUserService.getUser(myUserId);
        List<FanViolation> userViolations= myUser.getFanUser().getFanViolations();
        return userViolations;
    }

    //rework after violation class changes
 /*   public FanViolation getUserViolation(Integer myUserId, Integer fanViolationId){
        MyUser myUser = myUserService.getUser(myUserId);
        Violation violation= getViolation(violationId);

        List<FanViolation> userViolations= myUser.getFanUser().getFanViolations();

        for (FanViolation violationLoop: userViolations ) {
            if(violationLoop.getId() ==violation.getId()){
                return violationLoop;
            }
        }
       throw new ApiException("User doesn't have this violation ");
    }*/


    public Violation getViolation(Integer id) {

        Violation violation = violationRepository.findViolationById(id);
        if(violation ==null){
            throw new ApiException("Violation not found");
        }
        return violation;
    }


    public void addViolation(ObjectNode objectNode){
        String name = objectNode.get("name").asText();
        Double price = objectNode.get("price").asDouble();


        //we should add the date when we want to  assign the violation to user .. not here
      /*  ObjectMapper mapper = new ObjectMapper().
                registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        LocalDate violationDate = mapper.convertValue(objectNode.get("violationDate"), LocalDate.class);
*/
        Violation violation = new Violation(null,name,price,null);
        violationRepository.save(violation);
    }

    public void updateViolation(Integer id ,Violation violation){
        Violation currentViolation= violationRepository.findViolationById(id);
        if(currentViolation==null){
            throw new ApiException("Violation not found");
        }
        violation.setId(currentViolation.getId());
        violationRepository.save(violation);

    }
    public void  removeViolation(Integer id){
        Violation violation1=violationRepository.findViolationById(id);
        if(violation1==null){
            throw new ApiException("Violation not found");
        }
        violationRepository.deleteById(id);
    }


    public void assignToUser(ObjectNode objectNode) {
        Integer violationId = objectNode.get("violationId").asInt();
        Integer userId = objectNode.get("userId").asInt();

        Violation violation= getViolation(violationId);
        MyUser myUser= myUserService.getUser(userId);
        FanUser fanUser = myUser.getFanUser();


        FanViolation fanViolation = new FanViolation(null,LocalDate.now(),"unpaid",violation,fanUser);

        fanUser.setBlocked(true);

        fanUserRepository.save(fanUser);
        fanViolationRepository.save(fanViolation);

    }

    public void payForValidation(MyUser myUser, Integer fanViolationId, ObjectNode objectNode){
        Double amount = objectNode.get("amount").asDouble();

        MyUser myUser1 = myUserService.getUser(myUser.getId());
        FanViolation fanViolation = fanViolationRepository.findFanViolationById(fanViolationId);

        if(fanViolation.getViolationStatus().equals("paid")){
            throw new ApiException("The payment on this violation is done before");
        }

        if(fanViolation.getViolation().getPrice() != amount){
            throw new ApiException("Wrong violation amount");
        }

        fanViolation.setViolationStatus("paid");
        fanViolationRepository.save(fanViolation);

        if(unpaidViolationsCheck(myUser) == false){
            myUser1.getFanUser().setBlocked(false);
            myUserRepository.save(myUser1);
        }

    }


    public boolean unpaidViolationsCheck(MyUser myUser){
        List<FanViolation> fanViolations= getAllUserViolations(myUser.getId());

        for (FanViolation fanViolation :fanViolations) {
                if (fanViolation.getViolationStatus().equals("unpaid")){
                    return true;
                }
        }
        return false;
    }




}
