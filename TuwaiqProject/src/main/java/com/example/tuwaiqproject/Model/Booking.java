package com.example.tuwaiqproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotEmpty(message = "The status should not be empty")
    @Pattern(regexp="(inProgress|Pending|Valid)", message = "The role should be either inProgress or Valid")
   /* @Column(columnDefinition = "varchar(10) not null check (bookingStatus='inProgress' or bookingStatus= 'Pending' or bookingStatus= 'Valid')")*/
    private String bookingStatus;

    @NotNull
/*    @Column(columnDefinition = "double not null")*/
    private double price;



    @ManyToOne
    @JoinColumn(name = "fanUsers_id", referencedColumnName = "id")
    @JsonIgnore
    private FanUser fanUsers;

    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    @JsonIgnore
    private FootballMatch footballMatch;

    @ManyToOne
    @JoinColumn(name = "parking_id", referencedColumnName = "id")
    @JsonIgnore
    private Parking parking;

    @ManyToOne
    @JoinColumn(name = "seat_id", referencedColumnName = "id")
    @JsonIgnore
    private Seat seat;


    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", bookingStatus='" + bookingStatus + '\'' +
                ", fanUsers=" + fanUsers +
                ", footballMatch=" + footballMatch +
                ", parking=" + parking +
                ", seat=" + seat +
                '}';
    }
}
