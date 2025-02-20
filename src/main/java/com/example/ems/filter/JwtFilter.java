package com.example.ems.filter; 

import com.example.ems.service.JwtService;
import com.example.ems.repository.UserRepository;
import com.example.ems.entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //  Skip authentication for login, register, logout, and refresh-token requests
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/login") || requestURI.contains("/register") || 
            requestURI.contains("/logout") || requestURI.contains("/refresh-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        //  Extract JWT Token from Cookies
        String token = getJwtFromCookies(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return; // No JWT found, continue without authentication
        }

        //  Validate JWT and Authenticate User
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            String email = jwtService.extractEmail(token);
            
            if (email != null) {
                Users user = userRepository.findByEmail(email);
                
                if (user != null && jwtService.validateToken(token, email)) {
                    
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    
                    System.out.println("JWT Token from Cookies: " + token);
                    System.out.println("Extracted Email from JWT: " + email);
                    System.out.println("User Retrieved from DB: " + user);
                    System.out.println("Authentication Set in SecurityContext: " + SecurityContextHolder.getContext().getAuthentication());

                }
            }
        }

        //  Prevent NullPointerException when logging authorities
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            System.out.println("Granted Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        }

        filterChain.doFilter(request, response);
    }

    
    
    
    // Extract JWT Token from Cookies
    private String getJwtFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("access_token".equals(cookie.getName())) { // Use correct cookie name
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
