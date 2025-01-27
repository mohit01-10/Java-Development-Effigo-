package com.my.webapps.fwebapp.login;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

	public boolean authenticate (String name, String password) {
		
		boolean isValidname  = name.equalsIgnoreCase("abc");
		boolean isValidpass = password.equalsIgnoreCase("123asd");
		
		return isValidname && isValidpass;
	}
}
