package com.my.webapps.fwebapp.security;

import java.awt.im.InputContext;
import java.beans.BeanProperty;
import java.security.PublicKey;
import java.util.function.Function;

import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SpringSecConfiguration {

//	InMemoryUserDetailsManager
//	InMemoryUserDeatailsManager(UserDetails... users)

	
	@Bean
	public InMemoryUserDetailsManager createUserDetailsManager() {
		
		UserDetails userDetails = createNewUser("abc","123asd");
		UserDetails userDetails2 = createNewUser("efg","456fgh");
		return new InMemoryUserDetailsManager(userDetails,userDetails2);
	}

	private UserDetails createNewUser(String user, String pass) {
		Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);
		
		UserDetails userDetails = User.builder()
				.passwordEncoder(passwordEncoder )
				.username(user)
				.password(pass)
				.roles("USER","ADMIN")
				.build();
		return userDetails;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}



