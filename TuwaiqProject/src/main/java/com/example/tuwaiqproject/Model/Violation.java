package com.example.tuwaiqproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Violation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The name should not be empty")
    @Size(min = 3, message = "Enter valid violation name")
    @Column(columnDefinition = "varchar(15) not null")
    private String name;
    @NonNull
    @Column
    private double price;



    @OneToMany(cascade = CascadeType.ALL, mappedBy = "violation")
    private List<FanViolation> fanViolations;


}
