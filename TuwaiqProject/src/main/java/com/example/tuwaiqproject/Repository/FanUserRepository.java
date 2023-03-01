package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.FanUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FanUserRepository extends JpaRepository<FanUser, Integer> {

    FanUser findFanUsersById(Integer id);

}
