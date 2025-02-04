package com.assignment.loginpage.AuthDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRes {
	
    private String status;
    private String message;
    private Object data;
}

