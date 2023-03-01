package com.example.tuwaiqproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "The name should not be empty")
    @Size(min = 3, message = "Enter valid name")
    @Column(unique = true, columnDefinition = "varchar(15) not null")
    private String name;
    @Column
    @NotNull(message = "The total Seats Number should not be empty")
    private int totalSeatsNumber;
    @NotEmpty(message = "The city should not be empty")
    @Size(min = 3, message = "Enter valid city")
    @Column(columnDefinition = "varchar(15) not null")
    private String city;

    @NotNull(message = "The latitude should not be empty")
    private double latitude;

    @NotNull(message = "The longitude should not be empty")
    private double longitude;

    @OneToOne
    @JsonIgnore
    private StadiumAdmin stadiumAdmin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stadium")
    private List<Seat> seats;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stadium")
    private List<Parking> parkings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stadium")
    private List<FootballMatch> footballMatches;


}
