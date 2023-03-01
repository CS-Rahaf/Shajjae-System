package com.example.tuwaiqproject.RepositoryTest;

import com.example.tuwaiqproject.Model.Seat;
import com.example.tuwaiqproject.Repository.SeatRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SeatRepositoryTest {

    @Autowired
    SeatRepository seatRepository;

    Seat seat;

    @BeforeEach
    void setUp() {

        seat= new Seat(null,"VIP",808,1,1,null,null);

    }

    @Test
    public void findSeatByIdTesting(){
        seatRepository.save(seat);
        Seat seat1 = seatRepository.findSeatById(seat.getId());
        Assertions.assertThat(seat).isEqualTo(seat1);
    }

    @Test
    public void findSeatByUsernameTesting(){
        seatRepository.save(seat);
        Seat seat1= seatRepository.findSeatBySeatNumber(808);
        Assertions.assertThat(seat).isEqualTo(seat1);
    }

}
