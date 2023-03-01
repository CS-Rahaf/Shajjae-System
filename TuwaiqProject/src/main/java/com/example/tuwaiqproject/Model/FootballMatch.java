package com.example.tuwaiqproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FootballMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The firstClub should not be empty")
    @Size(min = 3, message = "Enter valid club name")
    @Column(columnDefinition = "varchar(15) not null")
    private String firstClub;

    @NotEmpty(message = "The secondClub should not be empty")
    @Size(min = 3, message = "Enter valid club name")
    @Column(columnDefinition = "varchar(15) not null")
    private String secondClub;

    @NotNull
    private LocalDate matchDate;
    @NotNull
    private LocalTime matchTime;

    @NotNull
    private double vipPrice;
    @NotNull
    private double classicPrice;


    @ManyToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    @JsonIgnore
    private Stadium stadium;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "footballMatch")
    private List<Booking> bookings;


}
