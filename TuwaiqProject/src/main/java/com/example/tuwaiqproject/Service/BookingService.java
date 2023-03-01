package com.example.tuwaiqproject.Service;

import com.example.tuwaiqproject.Exception.ApiException;
import com.example.tuwaiqproject.Model.*;
import com.example.tuwaiqproject.Repository.BookingRepository;
import com.example.tuwaiqproject.Repository.FanUserRepository;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor

public class BookingService {
    private final BookingRepository bookingRepository;
    private final MyUserService myUserService;
    private final FanUserRepository fanUserRepository;
    private final FootballMatchService footballMatchService;
    private final ParkingService parkingService;
    private final SeatService seatService;

    public List<Booking> getBookings(){
       return bookingRepository.findAll();
    }


    public Booking getBooking(Integer id){
        Booking booking = bookingRepository.findBookingById(id);
        if(booking ==null){
            throw new ApiException("Match not found");
        }
        return booking;
    }

    public List<Booking> getUserBookings(MyUser myUser){
        MyUser myUser1= myUserService.getUser(myUser.getId());
        FanUser fanUser= fanUserRepository.findFanUsersById(myUser1.getFanUser().getId());
        List<Booking> bookings= fanUser.getBookings();
        return bookings;
    }

    public Booking getUserBooking(MyUser myUser, Integer id){
        List<Booking> bookings= getUserBookings(myUser);
        Booking booking= new Booking();
        for (Booking bookingLoop: bookings) {
            if(bookingLoop.getId()==id){
                booking=bookingLoop;
                break;
            }
        }
        if(booking==null){
            throw new ApiException("Booking not found");
        }
        return booking;
    }



    public Booking addBooking(MyUser myUser, ObjectNode objectNode, boolean gift){
        Integer matchId = objectNode.get("matchId").asInt();
        Integer spotNumber = objectNode.get("spotNumber").asInt();
        Integer seatNumber = objectNode.get("seatNumber").asInt();

        FanUser fanUser = fanUserRepository.findFanUsersById(myUser.getFanUser().getId());

        if(fanUser.isBlocked()==true){
            throw new ApiException("Booking could not be processed because this user in block list due to an existing unpaid violation");
        }

        FootballMatch footballMatch = footballMatchService.getMatch(matchId);

        if(footballMatch.getMatchDate().isBefore(LocalDate.now())){
            throw new ApiException("Sorry this match is ended");
        }

        // we have to check that the user doesn't have another booking on the same match
        if(duplicateBookChecking(fanUser,footballMatch) == true && gift ==false){
            throw new ApiException("This user already has a ticket for this match");
        }

        Parking parking= parkingChecking(footballMatch, spotNumber);
        Seat seat = seatChecking(footballMatch, seatNumber);

        Booking booking;
        if(seat.getSeatType().equals("VIP")){
            booking = new Booking(null,"inProgress", footballMatch.getVipPrice(),fanUser,footballMatch,parking,seat);
            bookingRepository.save(booking);
            return booking;
        }
        else
            booking = new Booking(null,"inProgress", footballMatch.getClassicPrice(),fanUser,footballMatch,parking,seat);
            bookingRepository.save(booking);
            return booking;
    }

    public Parking parkingChecking(FootballMatch footballMatch, int spotNumber){
        Parking parking = parkingService.findParkingBySpotNumber(spotNumber);

        List<Booking> bookings = parking.getBookings();

        for (Booking booking : bookings) {
           if (booking.getFootballMatch().getId()==footballMatch.getId()) {
               throw new ApiException("This parking not available");
           }
        }
        return parking;
    }

    public Seat seatChecking(FootballMatch footballMatch, int seatNumber){
        Seat seat = seatService.findSeatBySeatNumber(seatNumber);

        List<Booking> bookings = seat.getBookings();

        for (Booking booking : bookings) {
            if (booking.getFootballMatch().getId()==footballMatch.getId()) {
                throw new ApiException("This seat not available");
            }
        }
        return seat;
    }

    public boolean duplicateBookChecking(FanUser fanUser, FootballMatch footballMatch){
        List<Booking> userBookings= fanUser.getBookings();

        for (Booking booking: userBookings) {
            if(booking.getFootballMatch().getId()==footballMatch.getId()){
                return true;
            }
        }
        return false;
    }

    public void payForBooking(MyUser myUser, Integer bookingId, ObjectNode objectNode) {
        Double amount = objectNode.get("amount").asDouble();
        Booking booking=getUserBooking(myUser,bookingId);

        if(booking.getBookingStatus().equals("Valid")){
            throw new ApiException("The payment on this booking is done before");
        }

        if(booking.getPrice() != amount){
            throw new ApiException("please enter correct amount");
        }
        booking.setBookingStatus("Valid");
        bookingRepository.save(booking);
    }

    public Booking giftBooking(MyUser myUser, ObjectNode objectNode){
        Booking booking =addBooking(myUser, objectNode, true);
        booking.setFanUsers(null);
        booking.setBookingStatus("Pending");
        bookingRepository.save(booking);
        return booking;
    }
    public void payForGift(MyUser myUser, Integer bookingId, ObjectNode objectNode) {
        MyUser myUser1 = myUserService.getUser(myUser.getId());
        Double amount = objectNode.get("amount").asDouble();
        Booking booking=getBooking(bookingId);

        if(booking.getBookingStatus().equals("Valid")){
            throw new ApiException("The payment on this booking is done before");
        }
        if(booking.getPrice() != amount){
            throw new ApiException("please enter correct amount");
        }
        bookingRepository.save(booking);
    }

    public void giftReception(MyUser myUser, Integer giftId){
        Booking booking = getBooking(giftId);
        MyUser myUser1 = myUserService.getUser(myUser.getId());
        FanUser fanUser = myUser1.getFanUser();

        if(booking.getBookingStatus().equals("Valid") || booking.getBookingStatus().equals("inProgress") ){
            throw new ApiException("Please enter a valid gift id");
        }

       if ( duplicateBookChecking(fanUser, booking.getFootballMatch()) == true){
           throw new ApiException("This user already has a ticket for this match");
       }
       booking.setFanUsers(fanUser);
       booking.setBookingStatus("Valid");
       bookingRepository.save(booking);
    }

}
