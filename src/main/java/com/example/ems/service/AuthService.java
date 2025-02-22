package com.example.ems.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ems.dto.AuthResponseDto;
import com.example.ems.dto.LoginRequestDto;
import com.example.ems.dto.RegisterRequestDto;
import com.example.ems.entity.LoginHistory;
import com.example.ems.entity.Role;
import com.example.ems.entity.Users;
import com.example.ems.enums.UserStatus;
import com.example.ems.repository.LoginHistoryRepository;
import com.example.ems.repository.RoleRepository;
import com.example.ems.repository.UserRepository;

import jakarta.mail.MessagingException;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    
    @Autowired
    private LoginHistoryRepository loginHistoryRepository; 
    
    @Autowired
    private EmailService emailService;
    
    
    
    /**
     * Register New User
     * 
     * @throws MessagingException 
     */
    public String registerUser(RegisterRequestDto request) throws MessagingException {
        // Check if email already exists
        Users existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            return "Email is already registered.";
        }
        
        // Fetch role using roleId
        Role userRole = roleRepository.findByRoleId(request.getRoleId());
        if (userRole == null) {
            return "Invalid role ID!";
        }
        
        // Determine status based on role
        UserStatus userStatus = (request.getRoleId() == 2) ? UserStatus.PENDING : UserStatus.ACTIVE;

        // Hash password with SHA-256 before bcrypt encoding
        String sha256HashedPassword = hashWithSHA256(request.getPassword());
        String finalEncodedPassword = passwordEncoder.encode(sha256HashedPassword); // Bcrypt encodes SHA-256 hash

        // Create new user & save to DB
        Users newUser = new Users();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(finalEncodedPassword); // Store double-hashed password
        newUser.setName(request.getName());
        newUser.setPhone(request.getPhone());
        newUser.setRole(userRole);
        newUser.setStatus(userStatus);

        userRepository.save(newUser);
        
        // Send approval request for role 2 users
        if (request.getRoleId() == 2) {
            String superAdminEmail = "letswork.mohit1@gmail.com"; // Replace with actual Super Admin email
            emailService.sendApprovalRequest(superAdminEmail, newUser.getName(), newUser.getUid());
        }
        
        return "User registered successfully!";
    }

    
    

    /**
     * Authenticate Login User & Generate JWT Token
     * 
     * @param request
     * @return
     */
    public AuthResponseDto authenticateUser(LoginRequestDto request) {
    	
        //Find user by email
        Users user = userRepository.findByEmail(request.getEmail());
        
        
        if (user == null) {
            return new AuthResponseDto(null, "User does not exist! Invalid User Email");
        }
        
       // System.out.println(user.getStatus());

        //Hash the input password with SHA-256 before matching
        String sha256HashedPassword = hashWithSHA256(request.getPassword());

        //Verify password (Compare hashed input with bcrypt stored password)
        if (!passwordEncoder.matches(sha256HashedPassword, user.getPassword())) {
            System.out.println("Password Mismatch! Hashed Input: " + sha256HashedPassword);
            return new AuthResponseDto(null, "Invalid password");
        }

        
        if (user.getStatus() != UserStatus.ACTIVE) {
            return new AuthResponseDto(null, "User is not Active");
        }


        String accessToken = jwtService.generateAccessToken(request.getEmail());
       
        
        //STORE LOGIN ATTEMPT IN LOGIN HISTORY TABLE
        LoginHistory loginHistory = new LoginHistory(user);
        loginHistoryRepository.save(loginHistory);

        return new AuthResponseDto(accessToken, "Login successful");
    }
    
        
    

    /**
     * Hash password using SHA256
     * 
     * @param password
     * @return
     */
    private String hashWithSHA256(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 Algorithm not found", e);
        }
    }
}
