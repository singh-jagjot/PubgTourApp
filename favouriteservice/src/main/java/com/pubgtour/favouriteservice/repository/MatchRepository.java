package com.pubgtour.favouriteservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pubgtour.favouriteservice.model.Match;

public interface MatchRepository extends JpaRepository<Match, Long>{
	List<Match> findByUserName(String userName);
	Match findByUserNameAndMatchId(String userName, String matchId);
}
