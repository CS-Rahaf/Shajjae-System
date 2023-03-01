package com.example.tuwaiqproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class StadiumAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JsonIgnore
    private MyUser myUser;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "stadiumAdmin")
    @PrimaryKeyJoinColumn
    private Stadium stadium;


    @Override
    public String toString() {
        return "StadiumAdmin{" +
                "id=" + id +
                '}';
    }
}
