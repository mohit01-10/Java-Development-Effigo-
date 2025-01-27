package com.my.webapps.jpa_and_hibernate_postgres.course;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "subject", schema = "courses")
public class Subject {
	
	@Id
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "author")
	private String author;
	
	
	public Subject() {
		
	}
	
	
	public Subject(long id, String name, String author) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
	}


	


	public long getId() {
		return id;
	}



	public String getName() {
		return name;
	}



	public String getAuthor() {
		return author;
	}

	

	public void setId(long id) {
		this.id = id;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + ", author=" + author + "]";
	}
	
	

	
	
	
}
