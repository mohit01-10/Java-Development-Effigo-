package com.example.ems.repository;


import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ems.entity.Users;
import com.example.ems.enums.UserStatus;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
	
    Users findByEmail(String email);

    Users findByUid(UUID id);
    
    List<Users> findAll();

	List<Users> findByStatus(UserStatus userStatus);
	
	List<Users> findByRole_Role(String role);
    
    
}
