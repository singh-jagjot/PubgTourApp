package com.pubgtour.userservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.pubgtour.userservice.model.User;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;
	private User user;
	
	@Before
	public void setUp() throws Exception {
		user = new User("active", "Active", "Singh", "klol", new Date());
	}
	@Test
	public void testRegisterUserSuccess() {
		userRepository.save(user);
		User testUser =  userRepository.findByUserName(user.getUserName());
		assertThat(testUser.equals(user));
	}
	
	@After
	public void cleanUp() throws Exception {
		userRepository.deleteAllInBatch();
	}
}
