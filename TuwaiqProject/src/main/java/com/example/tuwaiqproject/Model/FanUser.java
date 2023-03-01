package com.example.tuwaiqproject.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class FanUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "The name should not be empty")
    @Size(min = 3,max = 15, message = "Name between 3 and 15")
    private String name;

    @NotEmpty(message = "The phoneNumber should not be empty")
    @Size(min = 10,max = 10, message = "Enter valid phone number")
    @Column(unique = true, columnDefinition = "varchar(10) not null")
    private String phoneNumber;


    @Pattern(regexp="(Female|Male)", message = "The gender should be either Female or Male")
    @Column(columnDefinition = "varchar(6) not null check (gender= 'Female' or gender ='Male')")
    private String gender;

    @NotNull(message = "The disability should not be empty")
/*    @Column(columnDefinition = "boolean not null")*/
    private boolean disability;

    @NotNull(message = "The blocked should not be empty")
/*    @Column(columnDefinition = "boolean not null")*/
    private boolean blocked;





    @OneToOne
    @JsonIgnore
    private MyUser myUser;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fanUsers")
    private List<Booking> bookings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fanUser")
    private List<FanViolation> fanViolations;




    @Override
    public String toString() {
        return "FanUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", disability=" + disability +
                ", blocked=" + blocked +
                '}';
    }
}
