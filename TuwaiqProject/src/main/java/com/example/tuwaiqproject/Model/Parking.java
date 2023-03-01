package com.example.tuwaiqproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "The spotNumber should not be empty")
    private int spotNumber;

    @NotNull(message = "The floor should not be empty")
    private int floor;




    @ManyToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    @JsonIgnore
    private Stadium stadium;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parking")
    private List<Booking> bookings;


}
