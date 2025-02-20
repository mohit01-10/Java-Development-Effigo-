
package com.example.ems.service;

import com.example.ems.entity.RefreshToken;
import com.example.ems.entity.Users;
import com.example.ems.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;


    
    /**
     * Creates or updates a refresh token for the given user.
     * 
     * @param user
     * @return
     */
    @Transactional
    public RefreshToken createOrUpdateRefreshToken(Users user) {
        try {
            Optional<RefreshToken> existingTokenOpt = Optional.ofNullable(refreshTokenRepository.findByUser(user));

            RefreshToken refreshToken = existingTokenOpt.orElseGet(() -> new RefreshToken());
            refreshToken.setUser(user);
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusSeconds(60*10)); // 15 minutes expiry

            return refreshTokenRepository.save(refreshToken);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Error while creating/updating refresh token.", e);
        }
    }

    
    
    /**
     * Creates or updates a refresh token for the given user.
     * 
     * @param token
     * @return
     */
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    
    
    
    /**
     * Validates whether a refresh token is still valid.
     * 
     * @param refreshToken
     * @return
     */
    public boolean isTokenValid(RefreshToken refreshToken) {
        return refreshToken.getExpiryDate().isAfter(Instant.now());
    }
    
    

    /**
     * Deletes a user's refresh token (used for logout).
     * 
     * @param refreshToken
     */
    @Transactional
    public void deleteToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

}
