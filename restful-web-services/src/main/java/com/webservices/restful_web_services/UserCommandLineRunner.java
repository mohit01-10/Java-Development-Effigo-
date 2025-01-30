//package com.webservices.restful_web_services;
//
//import java.time.LocalDate;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import com.webservices.restful_web_services.jpa.UserRepository;
//import com.webservices.restful_web_services.user.User;
//
//@Component
//public class UserCommandLineRunner implements CommandLineRunner{
//
//	
//	@Autowired
//	private UserRepository repository;
//	
//	@Override
//	public void run(String... args) throws Exception{
//		repository.save(new User(null,"sdsdspringjpa", LocalDate.of(2023, 12, 12)));
//		repository.save(new User(null, "jdsdspa", LocalDate.of(2022, 11, 11)));
//
////		
//		repository.deleteById(53);
////		
//		System.out.println(repository.findById(3));
//		System.out.println(repository.findById(4));
//		
//	//	System.out.println(repository.findByAuthor("me"));
//		
//		
//	}
//	
//}
