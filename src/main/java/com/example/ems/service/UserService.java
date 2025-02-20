package com.example.ems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ems.entity.Users;
import com.example.ems.repository.UserRepository;

@Service
public class UserService {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	 
	 

	 /**
	  * Get User by Email
	  * 
	  * @param email
	  * @return
	  */
	 public Users getUserByEmail(String email) {
		 return userRepository.findByEmail(email);
	 }


	 /**
	  * Update and Save User
	  * 
	  * @param user
	  * @return
	  */
	 public Users updateUser(Users user) {
		 return userRepository.save(user);  
	 }


	 /**
	  * Encode Password
	  * 
	  * @param rawPassword
	  * @return
	  */
	 public String encodePassword(String rawPassword) {
		 return passwordEncoder.encode(rawPassword);
	 }

}


