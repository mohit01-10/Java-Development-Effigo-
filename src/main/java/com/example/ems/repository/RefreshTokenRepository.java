package com.example.ems.repository;

import com.example.ems.entity.RefreshToken;
import com.example.ems.entity.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(Users user);
    
    RefreshToken findByUser(Users user);
   

}

