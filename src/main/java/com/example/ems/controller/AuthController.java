
package com.example.ems.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ems.dto.AuthResponseDto;
import com.example.ems.dto.LoginRequestDto;
import com.example.ems.dto.RegisterRequestDto;
import com.example.ems.entity.RefreshToken;
import com.example.ems.entity.Users;
import com.example.ems.enums.UserStatus;
import com.example.ems.repository.UserRepository;
import com.example.ems.service.AuthService;
import com.example.ems.service.JwtService;
import com.example.ems.service.RefreshTokenService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;
  
	
	
	  /**
	 * @param request
	 * @return
	 * @throws MessagingException
	 */
	  @PostMapping("/register")
	  public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDto request) throws MessagingException {
	      String response = authService.registerUser(request);
	      return ResponseEntity.ok(response);
	  }

  
  
  
	  /**
	 * @param request
	 * @param response
	 * @return
	 */
	  @PostMapping("/login")
	  public ResponseEntity<?> loginUser(@RequestBody LoginRequestDto request, HttpServletResponse response) {
	      AuthResponseDto authResponse = authService.authenticateUser(request);
	      Users user = userRepository.findByEmail(request.getEmail());
	
	      if (user == null || authResponse.getToken() == null) {
	          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse.getMessage());
	      }
	      
	      if (user.getStatus() != UserStatus.ACTIVE) {
	    	  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authResponse.getMessage());
	        }

	
	      // Generate & store refresh token in DB
	      RefreshToken refreshToken = refreshTokenService.createOrUpdateRefreshToken(user);
	
	      //  Set Access Token in HttpOnly Cookie
	      addCookie(response, "access_token", authResponse.getToken(), 1 * 60); // 15 min expiry
	
	      //Return Refresh Token in response (frontend stores in memory/local storage)
	      Map<String, Object> responseData = new HashMap<>();
	      responseData.put("accessToken", authResponse.getToken());
	      responseData.put("refreshToken", refreshToken.getToken());
	      responseData.put("role", user.getRole().getRole());
	
	      return ResponseEntity.ok(responseData);
	  }

	  
	  
	  

    /**
     * @param requestBody
     * @param response
     * @return
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshAccessToken(@RequestBody Map<String, String> requestBody, HttpServletResponse response) {
        String refreshToken = requestBody.get("refreshToken");

        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token missing");
        }

        //Validate the refresh token
        Optional<RefreshToken> refreshTokenOptional = refreshTokenService.findByToken(refreshToken);
        if (refreshTokenOptional.isEmpty() || !refreshTokenService.isTokenValid(refreshTokenOptional.get())) {
        	refreshTokenService.deleteToken(refreshTokenOptional.get());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired refresh token");
        }

        //Generate new access token
        Users user = refreshTokenOptional.get().getUser();
        String newAccessToken = jwtService.generateAccessToken(user.getEmail());

        //Set new Access Token in cookie
        addCookie(response, "access_token", newAccessToken, 300);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    
    
    
    /**
     * @param response
     * @param requestBody
     * @return
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(HttpServletResponse response, @RequestBody Map<String, String> requestBody) {
        //Get refresh token from request body
        String refreshToken = requestBody.get("refreshToken");

        if (refreshToken != null) {
            Optional<RefreshToken> token = refreshTokenService.findByToken(refreshToken);
            token.ifPresent(refreshTokenService::deleteToken); //  This will now work correctly
        }

        //Clear Access Token Cookie
        removeCookie(response, "access_token");

        //Invalidate Spring Security Context (if applicable)
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok("Logged out successfully");
    }

    
    
    
    /**
     * @param token
     * @return
     */
    @GetMapping("/api/current-user")
  public ResponseEntity<?> getCurrentUser(@CookieValue(name = "access_token", required = false) String token) {
      if (token == null) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
      }

      String email = jwtService.extractEmail(token);
      Users user = userRepository.findByEmail(email);
      if (user == null) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
      }

      Map<String, Object> userData = new HashMap<>();
      userData.put("id", user.getUid());
      userData.put("name", user.getName());
      userData.put("email", user.getEmail());
      userData.put("phone", user.getPhone());
      userData.put("role", user.getRole().getRole());
      userData.put("status", user.getStatus().toString());


      return ResponseEntity.ok(userData);
  }
   
    
    
    
    
    /** 
     *  Adds an HTTP-only secure cookie.
     *  
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    private void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    

    
    /**
     * Removes a cookie by setting maxAge to 0.
     * 
     * @param response
     * @param name
     */
    private void removeCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
