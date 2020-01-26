package com.pubgtour.favouriteservice.services;

import java.util.List;

import com.pubgtour.favouriteservice.exceptions.MatchAlreadyExistsException;
import com.pubgtour.favouriteservice.exceptions.MatchNotFoundException;
import com.pubgtour.favouriteservice.model.Match;

public interface MatchService {
	boolean saveMatch(Match match) throws MatchAlreadyExistsException;
	boolean deleteByUserNameAndMatchId(String userName, String matchId) throws MatchNotFoundException;
	Match updateMatch(Match match) throws MatchNotFoundException;
	List<Match> getMyMatchList(String userName);
}
