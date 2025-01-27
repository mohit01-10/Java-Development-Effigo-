package com.my.webapps.jpa_and_hibernate_postgres.course.jpa;


import org.springframework.stereotype.Repository;

import com.my.webapps.jpa_and_hibernate_postgres.course.Subject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class SubjectJpaRepository {
	
	@PersistenceContext
	private EntityManager eM;
	
	
	public void insert(Subject subject) {
		eM.merge(subject); 
	}
	
	public Subject findById(long id) {
		return eM.find(Subject.class, id);
	}
	
	public void deleteById(long id) {
		Subject subject = eM.find(Subject.class, id);
		
		eM.remove(subject);
	}
}
