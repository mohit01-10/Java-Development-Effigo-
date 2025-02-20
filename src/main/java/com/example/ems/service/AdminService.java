package com.example.ems.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.example.ems.dto.RegisterRequestDto;
import com.example.ems.entity.Role;
import com.example.ems.entity.Users;
import com.example.ems.enums.UserStatus;
import com.example.ems.repository.FinancialDocumentRepository;
import com.example.ems.repository.LoginHistoryRepository;
import com.example.ems.repository.MessageRepository;
import com.example.ems.repository.RefreshTokenRepository;
import com.example.ems.repository.RoleRepository;
import com.example.ems.repository.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private LoginHistoryRepository loginHistoryRepository;
    
    @Autowired
	private FinancialDocumentRepository financialDocumentRepository;
    
    @Autowired
   	private MessageRepository messageRepository;
    
    @Autowired
   	private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private EmailService emailService;

    
    
    
    //  View all users
    /**
     * @return
     */
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }
    
    
    
    
    //  Check if user already exists
    /**
     * @param email
     * @return
     */
    public boolean userExists(String email) {
        return userRepository.findByEmail(email) != null;
    }


    
    
    //  Find User by ID (UUID)
    /**
     * @param userId
     * @return
     */
    public Users getUserById(UUID userId) {
        Users user = userRepository.findByUid(userId);
        return user;
    }

    
    
    
    //  Find Role by ID
    /**
     * @param roleId
     * @return
     */
    public Role getRoleById(int roleId) {
        return roleRepository.findByRoleId(roleId);
    }

    
    
    
    //  Update User in Database
    /**
     * @param user
     * @return
     */
    public Users updateUser(Users user) {
        return userRepository.save(user);
    } 
    
    
    
    
    //  Create and save new user
    /**
     * @param request
     * @return
     */
    public Users createUser(RegisterRequestDto request) {
    	
        Users newUser = new Users();
        
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPhone(request.getPhone());
        
        String sha256HashedPassword1 = hashWithSHA256(request.getPassword());
        String sha256HashedPassword2 = hashWithSHA256(sha256HashedPassword1);
        String finalEncodedPassword = passwordEncoder.encode(sha256HashedPassword2);
        
        newUser.setPassword(finalEncodedPassword);
        newUser.setStatus(UserStatus.ACTIVE); 
        
        //  Assign role based on roleId
        Role role = roleRepository.findByRoleId(request.getRoleId());
        newUser.setRole(role);


        return userRepository.save(newUser);
    }


    
     //  Delete a user
	 /**
	 * @param id
	 * @return
	 */
    @Transactional
    public boolean deleteUser(UUID id) {
    	Users user = userRepository.findByUid(id);
    	if (user == null) {
    		return false;
    	}

    	// Delete related Financial Documents
    	financialDocumentRepository.deleteByUser(user);

    	// Delete messages where the user is sender
    	messageRepository.deleteBySender(user);

    	// Delete messages where the user is receiver
    	messageRepository.deleteByReceiver(user);

    	// Delete login history of the user first
    	loginHistoryRepository.deleteByUser(user);

    	// Delete associated Refresh Token
    	refreshTokenRepository.deleteByUser(user);

    	// Delete the user
    	userRepository.delete(user);

    	return true;
    }

	 
	 
    // Change the user Status
	/**
	 * @param id
	 * @return
	 */
	public UserStatus toggleUserStatus(UUID id) {
		Users user=userRepository.findByUid(id);
		
		if(user == null) {
			return null;
		}			
		
		UserStatus newStatus;
        if (user.getStatus() == UserStatus.PENDING) {
            newStatus = UserStatus.ACTIVE;  // If PENDING, move to ACTIVE
        } else {
            newStatus = (user.getStatus() == UserStatus.ACTIVE) ? UserStatus.INACTIVE : UserStatus.ACTIVE;
        }
		user.setStatus(newStatus);
		userRepository.save(user);
		
		return newStatus;
		}
	
	
	
	
	// Fetch login history 
	 /**
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Map<String, Object>> getLoginHistory(int page, int size) {
	        PageRequest pageable = PageRequest.of(page, size);
	        
	        Page<Map<String, Object>> loginHistoryPage = loginHistoryRepository.findLoginHistory(pageable);
	        System.out.println(loginHistoryPage);
	        return loginHistoryPage.getContent();
	    }
    

    
	//  Aprrove new user
	/**
	 * @param userId
	 * @return
	 * @throws MessagingException
	 */
	public boolean approveUser(UUID userId) throws MessagingException {
		
		Users user = userRepository.findByUid(userId);
		
		if(user!=null && user.getStatus().equals(UserStatus.INACTIVE)) {
			user.setStatus(UserStatus.ACTIVE);
			userRepository.save(user);
			
			emailService.sendApproval(user.getEmail(), user.getName());
			
			return true;
		}
		return false;
	}
	
	
	
	//  SHA256 hashing
	/**
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



