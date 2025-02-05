package com.assignment.loginpage.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.loginpage.AuthDTO.AuthReq;
import com.assignment.loginpage.AuthDTO.AuthRes;
import com.assignment.loginpage.service.AuthService;
import com.assignment.loginpage.util.JwtUtil;



@CrossOrigin(origins = "http://127.0.0.1:5500") 
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<AuthRes> register(@RequestBody AuthReq request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthRes> login(@RequestBody AuthReq request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " prefix
        }
        jwtUtil.invalidateToken(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    
}
