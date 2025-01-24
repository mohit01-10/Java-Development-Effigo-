package com.my.webapps.jpa_and_hibernate_postgres.course;

import java.math.BigInteger;

public class Subject {

	private long id;
	private String name;
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
