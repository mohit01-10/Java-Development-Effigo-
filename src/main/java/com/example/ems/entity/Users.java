package com.example.ems.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.ems.enums.UserStatus;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails{
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @UuidGenerator // Generates a UUID automatically
    private UUID uid;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING) // ✅ Store as ENUM in DB
    @Column(nullable = false)
    private UserStatus status;  

    @Column(nullable = false, updatable = false)
    final LocalDateTime createdAt = LocalDateTime.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        System.out.println("Inside getAuthorities() in Users entity!");  
        System.out.println("Returning Role: " + role.getRole());  
        return List.of(new SimpleGrantedAuthority(role.getRole()));  // ✅ Ensure this returns "ROLE_NORMAL_USER"
    }

    @Override
    public String getUsername() {
        return this.email;  
    }
}	