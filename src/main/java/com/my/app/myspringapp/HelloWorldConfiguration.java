package com.my.app.myspringapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

record Person(String name, int age, Address add) {};

record Address(String addfl, String addsl) {};
@ Configuration
public class HelloWorldConfiguration {

	@Bean
	public String name() {
		return "ABC";

	}
	
	
	@Bean
	public int age() {
		return 23;
	}
	
	@Bean
	public Person person() {
		return new Person("ABC", 123, address());
		
	}
	
	@Bean
	public Address address() {
		return new Address("XYZ", "xyz");
	}
}
