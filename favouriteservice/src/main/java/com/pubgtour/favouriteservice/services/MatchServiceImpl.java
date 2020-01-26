package com.pubgtour.favouriteservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubgtour.favouriteservice.exceptions.MatchAlreadyExistsException;
import com.pubgtour.favouriteservice.exceptions.MatchNotFoundException;
import com.pubgtour.favouriteservice.model.Match;
import com.pubgtour.favouriteservice.repository.MatchRepository;

@Service
public class MatchServiceImpl implements MatchService {

	private final MatchRepository repo;
	
	@Autowired
	public MatchServiceImpl(MatchRepository repo) {
		this.repo = repo;
	}

	@Override
	public boolean saveMatch(Match match) throws MatchAlreadyExistsException {
		Match availMatch = repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId());
		if(availMatch != null){
			throw new MatchAlreadyExistsException("Sent match already exists");
		}
		repo.save(match);
		return true;
	}

	@Override
	public Match updateMatch(Match match) throws MatchNotFoundException {
		Match availMatch = repo.findByUserNameAndMatchId(match.getUserName(), match.getMatchId());
		if(availMatch == null){
			throw new MatchNotFoundException("No match found to update");
		}
		availMatch.setComments(match.getComments());
		repo.save(availMatch);
		return availMatch;
	}

	@Override
	public List<Match> getMyMatchList(String userName) {
		return repo.findByUserName(userName);
	}

	@Override
	public boolean deleteByUserNameAndMatchId(String userName, String matchId) throws MatchNotFoundException {
		Match storedMatch = repo.findByUserNameAndMatchId(userName, matchId);
		if(storedMatch == null){
			throw new MatchNotFoundException("Sent match not found");
		}
		repo.delete(storedMatch);
		return true;
	}

}
