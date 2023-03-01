package com.example.tuwaiqproject.RepositoryTest;

import com.example.tuwaiqproject.Model.MyUser;
import com.example.tuwaiqproject.Repository.MyUserRepository;
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
public class MyUserRepositoryTest {

    @Autowired
    MyUserRepository authRepository;

    MyUser myUser;

    @BeforeEach
    void setUp() {

        myUser=new MyUser(1,"SSSSS","Password","FAN",null,null);

    }

    @Test
    public void findMyUserByIdTesting(){
        authRepository.save(myUser);
        MyUser myUser1= authRepository.findMyUsersById(myUser.getId());

        Assertions.assertThat(myUser).isEqualTo(myUser1);
    }

    @Test
    public void findMyUserByUsernameTesting(){
        authRepository.save(myUser);
        MyUser myUser1= authRepository.findMyUserByUsername("SSSSS");

        Assertions.assertThat(myUser).isEqualTo(myUser1);
    }
}
