package com.pubgtour.userservice.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.pubgtour.userservice.exceptions.UserAlreadyExistsException;
import com.pubgtour.userservice.exceptions.UserNotFoundException;
import com.pubgtour.userservice.model.User;
import com.pubgtour.userservice.repository.UserRepository;


public class UserServiceTest {

	@Mock
	private UserRepository userRepository;
	private User user;
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	Optional<User> options;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = new User("active", "Active", "Singh", "klol", new Date());
		options = Optional.of(user);
	}
	@Test
	public void testSaveUserSuccess() throws UserAlreadyExistsException {
		when(userRepository.save(user)).thenReturn(user);
		boolean flag = userServiceImpl.saveUser(user);
		assertEquals("Cannot Register User", true, flag);
		verify(userRepository, times(1)).save(user);
	}
	@Test(expected = UserAlreadyExistsException.class)
	public void testSaveUserFailure() throws UserAlreadyExistsException {
		when(userRepository.findByUserName(user.getUserName())).thenReturn(user);
		when(userRepository.save(user)).thenReturn(user);
		@SuppressWarnings("unused")
		boolean flag = userServiceImpl.saveUser(user);
	}
	
	@Test
	public void testValidateSucess() throws UserNotFoundException {
		when(userRepository.findByUserNameAndPassword(user.getUserName(), user.getPassword())).thenReturn(user);
		User userResult=userServiceImpl.findByUserNameAndPassword(user.getUserName(),user.getPassword());
		assertNotNull(userResult);
		assertEquals("active",userResult.getUserName());
		verify(userRepository,times(1)).findByUserNameAndPassword(user.getUserName(), user.getPassword());
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testValidateFailure() throws UserNotFoundException{
		when(userRepository.findByUserName("active")).thenReturn(null);
		userServiceImpl.findByUserNameAndPassword(user.getUserName(), user.getPassword());
	}

}
