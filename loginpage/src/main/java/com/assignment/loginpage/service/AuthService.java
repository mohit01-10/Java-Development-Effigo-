package com.assignment.loginpage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assignment.loginpage.AuthDTO.AuthReq;
import com.assignment.loginpage.AuthDTO.AuthRes;
import com.assignment.loginpage.jpa.UserRepository;
import com.assignment.loginpage.user.User;
import com.assignment.loginpage.util.JwtUtil;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthRes registerUser(AuthReq request) {
        if (userRepository.findByName(request.getName()).isPresent()) {
            return new AuthRes("failed", "Username already exists", null);
        }
        User newUser = new User(request.getName(), passwordEncoder.encode(request.getPassword())); 
        userRepository.save(newUser);

        return new AuthRes("success", "User registered successfully", null);
    }


    public AuthRes loginUser(AuthReq request) {
        User user = userRepository.findByName(request.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthRes("failed", "Invalid credentials", null);
        }

        String token = jwtUtil.generateToken(user.getName());
        return new AuthRes("success", "Login successful", token);
    }
}
