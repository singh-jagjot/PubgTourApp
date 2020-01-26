package com.pubgtour.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pubgtour.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUserName(String userName);
	User findByUserNameAndPassword(String userName, String password);
}
