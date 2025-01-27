package com.my.webapps.jpa_and_hibernate_postgres.course.springjpa;

import java.beans.JavaBean;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.my.webapps.jpa_and_hibernate_postgres.course.Subject;

public interface SpringJpaRepository extends JpaRepository<Subject, Long>{

	List<Subject> findByAuthor(String author);

}
