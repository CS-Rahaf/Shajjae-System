package com.example.tuwaiqproject.RepositoryTest;

import com.example.tuwaiqproject.Model.Stadium;
import com.example.tuwaiqproject.Repository.StadiumRepository;
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
public class StadiumRepositoryTest {
    @Autowired
    StadiumRepository stadiumRepository;

    Stadium stadium;

    @BeforeEach
    void setUp() {

        stadium = new Stadium(null, "stadiumRepoTest", 100, "city", 10, 10, null, null, null, null);
    }

    @Test
    public void findStadiumByIdTesting() {
        stadiumRepository.save(stadium);

        Stadium stadium1 = stadiumRepository.findStadiumById(stadium.getId());
        System.out.println("hi");
        System.out.println(stadium1);
        Assertions.assertThat(stadium).isEqualTo(stadium1);
    }
}
