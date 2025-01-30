package com.aopprojects.spring_aop;

import java.lang.System.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aopprojects.spring_aop.aopexmple.business.BusinessService1;

@SpringBootApplication
public class SpringAopApplication implements CommandLineRunner{

	private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BusinessService1 businessService1;

	
	public static void main(String[] args) {
		SpringApplication.run(SpringAopApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		((org.slf4j.Logger) logger).info("Value returned is {}", businessService1.calculateMax());
		
		
	}

}
