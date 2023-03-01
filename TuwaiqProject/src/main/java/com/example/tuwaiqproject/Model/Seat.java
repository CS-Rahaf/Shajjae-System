package com.example.tuwaiqproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The type should not be empty")
    @Pattern(regexp="(CLASSIC|VIP)", message = "The seat type should be either CLASSIC or VIP")
   /* @Column(columnDefinition = "varchar(7) not null check (seatType= 'CLASSIC' or seatType ='VIP')")*/
    private String seatType;

    @NotNull(message = "The seat number should not be empty")
    private int seatNumber;

    @NotNull(message = "The group number should not be empty")
    private int seatGroup;

    @NotNull(message = "The row number should not be empty")
    private int seatRow;


/*
    @ManyToMany
    @JsonIgnore
    private List<FanUser> fanUsers;
*/

    @ManyToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    @JsonIgnore
    private Stadium stadium;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seat")
    private List<Booking> bookings;



}
