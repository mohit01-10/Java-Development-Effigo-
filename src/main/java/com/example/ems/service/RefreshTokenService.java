
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
            refreshToken.setExpiryDate(Instant.now().plusSeconds(60*10)); 

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



//
//package com.example.ems.service;
//
//import com.example.ems.entity.RefreshToken;
//import com.example.ems.entity.Users;
//import com.example.ems.repository.RefreshTokenRepository;
//
//
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.Instant;
//import java.util.Date;
//import java.util.Optional;
//
//import javax.crypto.SecretKey;
//
//@Service
//public class RefreshTokenService {
//	
//	private static final String SECRET_KEY = "6Tj1+XsZP3NCJ6p0A5GNR7XlC2mGJk5Rmr9lYshvGTY=";
//
//  @Autowired
//  private RefreshTokenRepository refreshTokenRepository;
//  @Autowired
//  private JwtService jwtService;
//
//
//  @Transactional
//  public RefreshToken createOrUpdateRefreshToken(Users user) {
//	  RefreshToken refreshToken1 = refreshTokenRepository.findByUser(user);
//	  
////	  System.out.println(refreshToken1.getId());
//	  if (refreshToken1 !=  null) {
//		  System.out.println("hi  "+refreshToken1.getId());
//		refreshTokenRepository.deleteByUserId(refreshToken1.getId());
//		System.out.println("delete   "+ refreshToken1);
//	}
//	  
//	  String jwtToken = generateRefreshToken(user);
//     // RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
//             // .orElse(new RefreshToken());
//      
//      RefreshToken refreshToken=new RefreshToken();
//      
//      refreshToken.setUser(user);
//      refreshToken.setToken(jwtToken);
//      refreshToken.setExpiryDate(Instant.now().plusSeconds(getRefreshTokenExpiry()));
//
//
//
//      return refreshTokenRepository.save(refreshToken);
//  }
//
//  private String generateRefreshToken(Users user) {
//	  //  long expirationTime = getRefreshTokenExpiry(); // Expiry time in seconds
//	  //  SecretKey key = Keys.hmacShaKeyFor(jwtService.getSecretKey().getBytes()); // Adjust to fetch key appropriately
//	    SecretKey key = getSignKey();
//	    
//	    return Jwts.builder()
//	            .subject(user.getEmail())  
//	            .issuedAt(new Date(System.currentTimeMillis())) 
////	            .expiration(new Date(System.currentTimeMillis() + 1000 * 1)) //15 min expiry
////	            .expiration(new Date(System.currentTimeMillis() + 1000 * 60  ))
//	            .signWith(getSignKey()) 
//	            .compact(); 
//  }
//
//
//
//	private long getRefreshTokenExpiry() {
//	    return 300; //seconds
//	}
//
//  public Optional<RefreshToken> findByToken(String token) {
//      return refreshTokenRepository.findByToken(token);
//  }
//
//  public boolean isTokenValid(RefreshToken refreshToken) {
//      return refreshToken.getExpiryDate().isAfter(Instant.now());
//  }
//
//  @Transactional
//  public void deleteToken(RefreshToken refreshToken) {
//      refreshTokenRepository.delete(refreshToken);
//  }
//  
//	private SecretKey getSignKey() {
//	    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//	    return Keys.hmacShaKeyFor(keyBytes);
//	}
//
//}
//
//
