package com.assignment.loginpage.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "userdetail", schema = "assignment")
public class User {
	
	public User() {
			
		}
		
	
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		@Column(nullable = false)
		@Size(min=2, message = "Name should have atleast 2 characters")
		private String name;
		
		@Column(nullable = false)
	    private String password;
		
		
		public User( String name, String password) {
			super();
			this.name = name;
			this.password = password;
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

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		


		
		
		

}
