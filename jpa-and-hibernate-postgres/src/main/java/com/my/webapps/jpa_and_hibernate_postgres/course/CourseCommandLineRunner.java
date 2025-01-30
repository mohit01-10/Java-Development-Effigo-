package com.my.webapps.jpa_and_hibernate_postgres.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.my.webapps.jpa_and_hibernate_postgres.course.Subject;
import com.my.webapps.jpa_and_hibernate_postgres.course.jpa.SubjectJpaRepository;
import com.my.webapps.jpa_and_hibernate_postgres.course.springjpa.SpringJpaRepository;

@Component
public class CourseCommandLineRunner implements CommandLineRunner{

//	@Autowired
//	private CourseJdbcRepository repository;
	
//	@Autowired
//	private SubjectJpaRepository repository;
	
	@Autowired
	private SpringJpaRepository repository;
	
	@Override
	public void run(String... args) throws Exception{
		repository.save(new Subject(9,"springjpa","me"));
		repository.save(new Subject(10,"jpa","me"));
		
		repository.deleteById(4l);
		
		System.out.println(repository.findById(5l));
		System.out.println(repository.findById(6l));
		
	//	System.out.println(repository.findByAuthor("me"));
		
		
	}
	
}
