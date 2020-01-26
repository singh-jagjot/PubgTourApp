package com.pubgtour.userservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pubgtour.userservice.exceptions.UserAlreadyExistsException;
import com.pubgtour.userservice.exceptions.UserNotFoundException;
import com.pubgtour.userservice.model.User;
import com.pubgtour.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

private final UserRepository userRepo;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	@Override
	public boolean saveUser(User user) throws UserAlreadyExistsException {
		User haveUser = userRepo.findByUserName((user.getUserName()));
		if(haveUser != null) {
			throw new UserAlreadyExistsException("User with Id already exists");
		}
		userRepo.save(user);
		return true;
	}

	@Override
	public User findByUserNameAndPassword(String userName, String password) throws UserNotFoundException {
		User user = userRepo.findByUserNameAndPassword(userName, password);
		if(user == null){
			throw new UserNotFoundException("UserId and Password mismatch");
		}
		return user;
	}

}
