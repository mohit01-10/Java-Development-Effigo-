package com.example.ems.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "login_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistory {
    
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID loginId;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private Users user;

    @Column(nullable = false)
    private LocalDateTime loginTime;


    public LoginHistory(Users user) {
        this.user = user;
        this.loginTime = LocalDateTime.now();
    }
}
