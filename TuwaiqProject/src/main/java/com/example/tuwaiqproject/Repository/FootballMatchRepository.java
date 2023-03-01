package com.example.tuwaiqproject.Repository;

import com.example.tuwaiqproject.Model.FootballMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FootballMatchRepository extends JpaRepository<FootballMatch, Integer> {

    FootballMatch findFootballMatchById(Integer id);
    FootballMatch  findFootballMatchByFirstClubOrSecondClub(String firstClub ,String SecondClub);

    List<FootballMatch> findAllByFirstClubOrSecondClub(String firstClub, String secondClub);
    FootballMatch findFootballMatchByFirstClub(String firstClub);
}
