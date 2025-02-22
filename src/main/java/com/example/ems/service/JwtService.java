package com.example.ems.service;

import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "6Tj1+XsZP3NCJ6p0A5GNR7XlC2mGJk5Rmr9lYshvGTY=FTJVvjGAgtF";

    
    /**
     * @param email
     * @return
     */
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .signWith(getSignKey())
                .compact();
    }
    
    
    /**
     * @param token
     * @return
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    
    
    /**
     * @param token
     * @param email
     * @return
     */
    public boolean validateToken(String token, String email) {
        return (extractEmail(token).equals(email) && !isTokenExpired(token));
    }

    
    
    /**
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    
    
    /**
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    
    
    /**
     * @param token
     * @return
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    
    /**
     * @return
     */
    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        System.out.println(Keys.hmacShaKeyFor(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

