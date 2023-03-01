package com.example.tuwaiqproject.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FanViolation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column
    private LocalDate violationDate;

    @NotEmpty
    @Pattern(regexp="(unpaid|paid)", message = "The role should be either paid or unpaid")
  /*  @Column(columnDefinition = "varchar(6) not null check (violationStatus= 'unpaid' or violationStatus ='paid')")*/
    private String violationStatus;



    @ManyToOne
    @JoinColumn(name = "violation_id", referencedColumnName = "id")
    @JsonIgnore
    private Violation violation;


    @ManyToOne
    @JoinColumn(name = "fanUser_id", referencedColumnName = "id")
    @JsonIgnore
    private FanUser fanUser;

    @Override
    public String toString() {
        return "FanViolation{" +
                "id=" + id +
                ", violationDate=" + violationDate +
                ", violationStatus='" + violationStatus + '\'' +
                '}';
    }
}
