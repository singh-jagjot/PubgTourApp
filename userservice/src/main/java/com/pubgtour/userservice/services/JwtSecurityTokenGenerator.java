package com.pubgtour.userservice.services;

import java.util.Map;

import com.pubgtour.userservice.model.User;


public interface JwtSecurityTokenGenerator {
	Map<String, String> generateToken(User user);
}
