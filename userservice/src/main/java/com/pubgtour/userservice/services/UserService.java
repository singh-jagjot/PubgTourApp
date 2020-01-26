package com.pubgtour.userservice.services;

import com.pubgtour.userservice.exceptions.UserAlreadyExistsException;
import com.pubgtour.userservice.exceptions.UserNotFoundException;
import com.pubgtour.userservice.model.User;

public interface UserService {
	boolean saveUser(User user) throws UserAlreadyExistsException;
	User findByUserNameAndPassword(String userName, String password) throws UserNotFoundException;
}
