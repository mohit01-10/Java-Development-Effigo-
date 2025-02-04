package com.assignment.loginpage.util;

import java.util.Date;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "your_secret_key_strong_key_for_strong_encryption"; // You can keep a base string or passphrase

    // Generate a secure secret key with appropriate size (256 bits for HS256)
    private final byte[] secretKeyBytes = SECRET_KEY.getBytes(); // Convert key to bytes for better security
    private final SecretKey signingKey = Keys.hmacShaKeyFor(secretKeyBytes); // Secure key for HMAC-SHA256

    // Method for generating token
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiry
                .signWith(signingKey, SignatureAlgorithm.HS256) // Use generated signing key
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey) // Use generated signing key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Validate the token
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    // Check if token is expired
    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey) // Use generated signing key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date(System.currentTimeMillis()));
    }
}
