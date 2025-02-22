package com.example.ems.service;


import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.ems.dto.UserExcelDto;
import com.example.ems.entity.Role;
import com.example.ems.entity.Users;
import com.example.ems.enums.UserStatus;
import com.example.ems.repository.RoleRepository;
import com.example.ems.repository.UserRepository;

@Service
public class BulkUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    

    /**
     * Bulk register from excel
     * 
     * @param file
     * @return
     */    
//    public Map<String, Object> registerUsersFromExcel(MultipartFile file) {
//        Map<String, Object> response = new HashMap<>();
//        List<String> skippedUsers = new ArrayList<>();
//        List<String> registeredUsers = new ArrayList<>();
//
//        try {
//            if (!ExcelService.hasExcelFormat(file)) {
//                response.put("message", "Invalid file format. Please upload an Excel file.");
//                return response;
//            }
//
//            InputStream is = file.getInputStream();
//            List<UserExcelDto> userDtos = ExcelService.excelToUsers(is);
//
//            for (UserExcelDto dto : userDtos) {
//                // Skip empty or null emails
//                if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
//                    continue;
//                }
//
//                if (userRepository.findByEmail(dto.getEmail()) != null) {
//                    skippedUsers.add(dto.getEmail().trim()); // Collect existing emails
//                    continue;
//                }
//
//                String sha256HashedPassword1 = hashWithSHA256(dto.getPassword());
//                String sha256HashedPassword2 = hashWithSHA256(sha256HashedPassword1);
//                String finalEncodedPassword = passwordEncoder.encode(sha256HashedPassword2);
//
//                Role role = roleRepository.findById(dto.getRoleId())
//                        .orElseThrow(() -> new RuntimeException("Invalid Role ID"));
//
//                Users user = Users.builder()
//                        .name(dto.getName())
//                        .email(dto.getEmail().trim()) // Ensure email is trimmed
//                        .password(finalEncodedPassword)
//                        .role(role)
//                        .status(UserStatus.ACTIVE)
//                        .build();
//
//                userRepository.save(user);
//                registeredUsers.add(dto.getEmail().trim()); // Collect only valid emails
//            }
//
//            response.put("message", "Users processed successfully.");
//            response.put("registeredUsers", registeredUsers);
//            response.put("skippedUsers", skippedUsers);
//
//            return response;
//
//        } catch (Exception e) {
//            response.put("message", "Error processing file: " + e.getMessage());
//            return response;
//        }
//    }
    
    public Map<String, Object> registerUsersFromExcel(MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        List<String> skippedUsers = new ArrayList<>();
        List<String> registeredUsers = new ArrayList<>();

        try {
            if (!ExcelService.hasSupportedFormat(file)) {
                response.put("message", "Invalid file format. Please upload an Excel or CSV file.");
                return response;
            }

            InputStream is = file.getInputStream();
            List<UserExcelDto> userDtos;

            if (file.getContentType().equals("text/csv") || file.getContentType().equals("application/csv")) {
                userDtos = ExcelService.csvToUsers(is); 
            } else {
                userDtos = ExcelService.excelToUsers(is); 
            }

            for (UserExcelDto dto : userDtos) {
                if (dto.getName() == null || dto.getName().trim().isEmpty() ||
                    dto.getEmail() == null || dto.getEmail().trim().isEmpty() ||
                    dto.getPassword() == null || dto.getPassword().trim().isEmpty() ||
                    (dto.getRoleId() != 1 && dto.getRoleId() != 2)) { 
                    continue;
                }

                if (userRepository.findByEmail(dto.getEmail().trim()) != null) {
                    skippedUsers.add(dto.getEmail().trim());
                    continue;
                }

                String sha256HashedPassword1 = hashWithSHA256(dto.getPassword().trim());
                String sha256HashedPassword2 = hashWithSHA256(sha256HashedPassword1);
                String finalEncodedPassword = passwordEncoder.encode(sha256HashedPassword2);

                Role role = roleRepository.findById(dto.getRoleId())
                        .orElseThrow(() -> new RuntimeException("Invalid Role ID"));

                Users user = Users.builder()
                        .name(dto.getName().trim())
                        .email(dto.getEmail().trim())
                        .password(finalEncodedPassword)
                        .role(role)
                        .status(UserStatus.ACTIVE)
                        .build();

                userRepository.save(user);
                registeredUsers.add(dto.getEmail().trim());
            }

            response.put("message", "Users processed successfully.");
            response.put("registeredUsers", registeredUsers);
            response.put("skippedUsers", skippedUsers);

        } catch (Exception e) {
            response.put("message", "Error processing file: " + e.getMessage());
        }

        return response;
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