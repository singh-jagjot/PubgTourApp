package com.pubgtour.userservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pubgtour.userservice.model.User;
import com.pubgtour.userservice.services.JwtSecurityTokenGenerator;
import com.pubgtour.userservice.services.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

	@Autowired
	private transient MockMvc mockMvc;
	
	@MockBean
	private transient UserService userService;
	
	@MockBean
	private JwtSecurityTokenGenerator securityTokenGenerator;
	
	private User user;
	
	@InjectMocks
	UserController userController;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.initMocks(this);
		user = new User("active", "Active", "Singh", "klol", new Date());
	}

	@Test
	public void testRegisterUser() throws Exception {
		when(userService.saveUser(user)).thenReturn(true);
		mockMvc.perform(post("/api/user/register").contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON).content(jsonToString(user))).andExpect(status().isCreated())
				.andDo(print());
		verify(userService, times(1)).saveUser(Mockito.any(User.class));
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void testLoginUser() throws Exception {
		String userName = "active";
		String password = "klol";
		when(userService.saveUser(user)).thenReturn(true);
		when(userService.findByUserNameAndPassword(userName, password)).thenReturn(user);
		mockMvc.perform(post("/api/user/login").contentType(MediaType.APPLICATION_JSON)
				.content(jsonToString(user))).andExpect(status().isOk());
		verify(userService).findByUserNameAndPassword(user.getUserName(), user.getPassword());
		verifyNoMoreInteractions(userService);
	}

	private static String jsonToString(final Object obj) throws JsonProcessingException {
		String result;
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			result = jsonContent;
		}
		catch (JsonProcessingException e) {
			result = "json processing error";
		}
		return result;
	}
    
}
