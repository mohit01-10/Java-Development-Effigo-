package com.my.webapps.jpa_and_hibernate_postgres.course.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.my.webapps.jpa_and_hibernate_postgres.course.Subject;

@Component
public class CourseJdbcCommandLineRunner implements CommandLineRunner{

	@Autowired
	private CourseJdbcRepository repository;
	
	@Override
	public void run(String... args) throws Exception{
//		repository.insert(new Subject(6,"abc","me"));
//		repository.insert(new Subject(7,"efg","me"));
//		repository.insert(new Subject(8,"hij","me"));
		
		repository.deleteById(2);
		
		System.out.println(repository.findById(3));
		System.out.println(repository.findById(4));
		
	}
	
}
