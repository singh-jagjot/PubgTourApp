package com.pubgtour.favouriteservice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pubgtour.favouriteservice.exceptions.MatchAlreadyExistsException;
import com.pubgtour.favouriteservice.exceptions.MatchNotFoundException;
import com.pubgtour.favouriteservice.model.Match;
import com.pubgtour.favouriteservice.services.MatchService;

import io.jsonwebtoken.Jwts;

@CrossOrigin
@RestController
@RequestMapping("/api/match")
public class MatchController {

	private MatchService service;
	@Autowired
	public MatchController(MatchService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<?> saveMatch(@RequestBody final Match match, HttpServletRequest request){
		ResponseEntity<?> responseEntity;
		final String authHeader = request.getHeader("Authorization");
		final String token = authHeader.substring(7);
		String userName = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		try{
			match.setUserName(userName);
			service.saveMatch(match);
			responseEntity = new ResponseEntity<Match>(match, HttpStatus.CREATED);
		} catch(MatchAlreadyExistsException e){
			responseEntity = new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	@DeleteMapping(value = "/{matchId}")
	public ResponseEntity<?> deleteMovieById(@PathVariable final String matchId, HttpServletRequest request){
		ResponseEntity<?> responseEntity;
		final String authHeader = request.getHeader("Authorization");
		final String token = authHeader.substring(7);
		String userName = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		try{
			service.deleteByUserNameAndMatchId(userName, matchId);
			responseEntity =  new ResponseEntity<String>("{ \"message\": \"Movie deleted successfully\"}", HttpStatus.OK);
		} catch(MatchNotFoundException e){
			responseEntity =  new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.NOT_FOUND);
		}
		
		return responseEntity;
	}
	
	@PutMapping
	public ResponseEntity<?> updateMovie(@RequestBody final Match match, HttpServletRequest request){
		ResponseEntity<?> responseEntity;
		final String authHeader = request.getHeader("Authorization");
		final String token = authHeader.substring(7);
		String userName = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		match.setUserName(userName);
		try{
			Match fetchedMatch = service.updateMatch(match);
			responseEntity = new ResponseEntity<Match>(fetchedMatch, HttpStatus.OK);
		} catch(MatchNotFoundException e){
			responseEntity =  new ResponseEntity<String>("{ \"message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
		return responseEntity;
	}
	
	@GetMapping
	public ResponseEntity<?> fetchMyMovies(HttpServletRequest request){
		final String authHeader = request.getHeader("Authorization");
		final String token = authHeader.substring(7);
		String userName = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody().getSubject();
		return new ResponseEntity<List<Match>>(service.getMyMatchList(userName), HttpStatus.OK);
	}
}
