package com.assignment.loginpage.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assignment.loginpage.user.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	Optional<User> findByName(String name);

}
