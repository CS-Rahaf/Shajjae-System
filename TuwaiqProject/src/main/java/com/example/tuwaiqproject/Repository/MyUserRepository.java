package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer> {

    MyUser findMyUsersById(Integer id);
    MyUser findMyUserByUsername(String username);

}
