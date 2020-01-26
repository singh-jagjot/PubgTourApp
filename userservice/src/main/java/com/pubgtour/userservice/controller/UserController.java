package com.pubgtour.userservice.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pubgtour.userservice.model.User;
import com.pubgtour.userservice.services.JwtSecurityTokenGenerator;
import com.pubgtour.userservice.services.UserService;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

	private UserService userService;
	private JwtSecurityTokenGenerator tokenGenerator;
	@Autowired
	public UserController(UserService userService, JwtSecurityTokenGenerator tokenGenerator) {
		this.userService = userService;
		this.tokenGenerator = tokenGenerator;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		try {
			userService.saveUser(user);
			return new ResponseEntity<String>("User registered successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"message\": \"" + e.getMessage() + "\"}", HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User loginDetail) {
		try {
			String userName = loginDetail.getUserName();
			String password = loginDetail.getPassword();
			if (userName == null || password == null) {
				throw new Exception("Username or Password cannot be empty");
			}
			
			User user = userService.findByUserNameAndPassword(userName, password);
			
			if (user == null) {
				throw new Exception("User with given Id does not exists");
			}
			Map<String, String> map = tokenGenerator.generateToken(user);
			return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("{\"message\": \"" + e.getMessage() + "\"}", HttpStatus.UNAUTHORIZED);
		}
	}
}
