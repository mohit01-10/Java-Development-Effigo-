package com.example.ems.controller;

import com.example.ems.dto.EditUserDto;
import com.example.ems.dto.RegisterRequestDto;
import com.example.ems.entity.FinancialDocument;
import com.example.ems.entity.Role;
import com.example.ems.entity.Users;
import com.example.ems.enums.UserStatus;
import com.example.ems.service.AdminService;
import com.example.ems.service.BulkUserService;
import com.example.ems.service.FinancialDocumentService;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")

public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private FinancialDocumentService documentService;
    
    @Autowired
    private BulkUserService  bulkUserService;

    
    
    //  VIEW ALL USERS
    /**
     * @return
     */
    @GetMapping("/users")
    public ResponseEntity<List<Map<String, Object>>> getAllUsers() {
        List<Users> users = adminService.getAllUsers();

        
        List<Map<String, Object>> userList = users.stream()
                .filter(user -> !"superAdmin".equalsIgnoreCase(user.getRole().getRole())) // Exclude superAdmin
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("uid", user.getUid());
                    userMap.put("name", user.getName());
                    userMap.put("email", user.getEmail());
                    userMap.put("phone", user.getPhone());
                    userMap.put("role", user.getRole().getRole());
                    userMap.put("status", user.getStatus().toString());
                    return userMap;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(userList);
    }
    
    
    
   
    
    //  EDIT A USERS
    /**
     * @param userId
     * @param request
     * @return
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> editUser(@PathVariable("id") UUID userId,@RequestBody EditUserDto request) {

        // Find user by UUID
        Users user = adminService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }

        
        // Update User Details
        if (request.getName() != null) user.setName(request.getName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getEmail() != null) user.setEmail(request.getEmail());
        
        if (request.getRole() != null) {
            Role role = adminService.getRoleById(request.getRole());
            if (role == null) {
                return ResponseEntity.status(400).body(Map.of("error", "Invalid role ID"));
            }
            user.setRole(role);
        }

        
        // Save Updated User
        Users updatedUser = adminService.updateUser(user);

        
        // Return Response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User updated successfully");
        response.put("user", Map.of(
                "uid", updatedUser.getUid(),
                "name", updatedUser.getName(),
                "email", updatedUser.getEmail(),
                "phone", updatedUser.getPhone(),
                "role", updatedUser.getRole().getRole()              
        ));

        return ResponseEntity.ok(response);
    }
    
    
 
    
    
    //  DELETE A  USERS
    /**
     * @param id
     * @return
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id){
    	boolean isDeleteted = adminService.deleteUser(id);
    	
    	if(!isDeleteted) {
    		return ResponseEntity.status(404).body("User not found");
    	}
    	return ResponseEntity.ok("User deleted successfully");
    }

  
    
    
    
    //  CHANGE STATUS OF A USER
    /**
     * @param id
     * @return
     */
    @PutMapping("/users/{id}/status")
    public ResponseEntity<String> toggleUserStatus(@PathVariable UUID id) {
        UserStatus updatedStatus = adminService.toggleUserStatus(id);

        if (updatedStatus == null) {
            return ResponseEntity.status(404).body("User not found");
        }

        return ResponseEntity.ok("User status updated to: " + updatedStatus);
    }
    
    
    
    
    
    
    //  UPLOAD DOCUMENT OF A USER
    /**
     * @param userId
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload-document/{userId}")
    public ResponseEntity<String> uploadDocument(
            @PathVariable UUID userId,
            @RequestParam("file") MultipartFile file) throws IOException {
        
        String response = documentService.uploadDocument(userId, file);
        return ResponseEntity.ok(response);
    }

    
    
    
    
    //  ADD A USER
    /**
     * @param request
     * @return
     */
    @PostMapping("/add-user")
    public ResponseEntity<Map<String, Object>> registerNewUser(@RequestBody RegisterRequestDto request) {

        //  Check if email already exists
        if (adminService.userExists(request.getEmail())) {
            return ResponseEntity.status(400).body(Map.of("error", "User with this email already exists"));
        }

        //  Create new user
        Users newUser = adminService.createUser(request);

        //  Return Response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("user", Map.of(
            "uid", newUser.getUid(),
            "name", newUser.getName(),
            "email", newUser.getEmail(),
            "phone", newUser.getPhone(),
            "role", newUser.getRole().getRole(),
            "status", newUser.getStatus().toString()
        ));
        
        return ResponseEntity.ok(response);
    }
    
    
    
    
    
    
    //  GET LOGIN HISTORY
    /**
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/logging")
    public ResponseEntity<List<Map<String, Object>>> getLoginHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Map<String, Object>> history = adminService.getLoginHistory(page, size);
        return ResponseEntity.ok(history);
    }
    
    
    
    
    
    //  APPROVE A USER
    /**
     * @param userId
     * @return
     * @throws MessagingException
     */
    @GetMapping("/approve-user/{userId}")
    public ResponseEntity<String> approveUser(@PathVariable UUID userId) throws MessagingException{
    	
    	boolean update = adminService.approveUser(userId);
    	
    	if(update) {
    		return ResponseEntity.ok("User approved successfully ");
    	}
    	
    	return ResponseEntity.ok("User not found");
    }
    
    
    
    
    
    //  BULK REGISTER 
    /**
     * @param file
     * @return
     */
    @PostMapping("/bulk-register")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = bulkUserService.registerUsersFromExcel(file);
        return ResponseEntity.ok(response);
    }


    
    

    
    //  VIEW DOCUMNET OF A USERS
    /**
     * @param userId
     * @return
     */
    @GetMapping("/documents/{userId}")
    public ResponseEntity<List<FinancialDocument>> getUserDocuments(@PathVariable String userId) {
        return ResponseEntity.ok(documentService.getUserDocuments(UUID.fromString(userId)));
    }

}
