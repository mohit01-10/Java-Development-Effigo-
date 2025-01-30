package com.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "userdetail", schema = "courses")
public class User {
	
	protected User() {
		
	}
	

	@Id
	private Integer id;
	
	@Column
	@Size(min=2, message = "Name should have atleast 2 characters")
	private String name;
	
	@Column
	@Past(message = "Birth Date should be in the past")
	private LocalDate bdate;
	
	@OneToMany(mappedBy = "user")
	@JsonIgnore // because we are using user in path name, and we do not need posts
	private List<Post> posts;
	
	public User(Integer id, String name, LocalDate bdate) {
		super();
		this.id = id;
		this.name = name;
		this.bdate = bdate;
	}
	
	 public User(String name, LocalDate bdate) {
	        this.name = name;
	        this.bdate = bdate;
	    }



	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LocalDate getbDate() {
		return bdate;
	}


	public void setbDate(LocalDate bdate) {
		this.bdate = bdate;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", bdate=" + bdate + "]";
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	
	
}
