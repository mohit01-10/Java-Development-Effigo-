package com.example.ems.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.dto.UpdateUserDto;
import com.example.ems.entity.FinancialDocument;
import com.example.ems.entity.Users;
import com.example.ems.enums.UserStatus;
import com.example.ems.service.FinancialDocumentService;
import com.example.ems.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
    private UserService userService;
	@Autowired 
	private FinancialDocumentService documentService;
	
	
	
	
    /**
     * @param user
     * @return
     */
	@GetMapping("/profile")
	public ResponseEntity<Map<String, Object>> getUserProfile(@AuthenticationPrincipal Users user) {
		
	    ResponseEntity<Map<String, Object>> statusCheck = checkUserActive(user);
	    
	    if (statusCheck != null) return statusCheck; 

	    Map<String, Object> response = new HashMap<>();
	    response.put("uid", user.getUid());
	    response.put("name", user.getName());
	    response.put("email", user.getEmail());
	    response.put("phone", user.getPhone());
	    response.put("role", user.getRole().getRole());
	    response.put("status", user.getStatus().toString());

	    return ResponseEntity.ok(response);
	}


	

    
    /**
     * @param user
     * @param request
     * @return
     */
	@PutMapping("/profile")
	public ResponseEntity<Map<String, Object>> editUserProfile(@AuthenticationPrincipal Users user, @RequestBody UpdateUserDto request) {
		
	    ResponseEntity<Map<String, Object>> statusCheck = checkUserActive(user);
	    
	    if (statusCheck != null) return statusCheck;

	    Users existingUser = userService.getUserByEmail(user.getEmail());
	    
	    if (existingUser == null) {
	        return ResponseEntity.status(404).body(Map.of("error", "User not found"));
	    }

	    if (request.getName() != null) existingUser.setName(request.getName());
	    if (request.getPhone() != null) existingUser.setPhone(request.getPhone());

	    Users updatedUser = userService.updateUser(existingUser);

	    Map<String, Object> response = new HashMap<>();
	    response.put("uid", updatedUser.getUid());
	    response.put("name", updatedUser.getName());
	    response.put("email", updatedUser.getEmail());
	    response.put("phone", updatedUser.getPhone());
	    response.put("role", updatedUser.getRole().getRole());
	    response.put("status", updatedUser.getStatus().toString());
	    response.put("message", "Profile updated successfully");

	    return ResponseEntity.ok(response);
	}



    
	
    /**
     * @param user
     * @return
     */
	@GetMapping("/documents")
	public ResponseEntity<?> getUserDocuments(@AuthenticationPrincipal Users user) {
		
	    ResponseEntity<Map<String, Object>> statusCheck = checkUserActive(user);
	    
	    if (statusCheck != null) return statusCheck;

	    return ResponseEntity.ok(documentService.getUserDocuments(user.getUid()));
	}
	
	

    
    
    
	
    /**
     * @param user
     * @return
     */
    private ResponseEntity<Map<String, Object>> checkUserActive(Users user) {
        if (user == null) {
            return ResponseEntity.status(403).body(Map.of("error", "Unauthorized"));
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            return ResponseEntity.status(403).body(Map.of("error", "Unauthorized! User is INACTIVE"));
        }

        return null; 
    }

}

