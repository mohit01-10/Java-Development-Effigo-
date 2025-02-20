package com.example.ems.entity;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "financialdoc")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinancialDocument {
    
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID docId;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    private Users user;

    @Column(nullable = false)
    private String docName;

    @Column(nullable = false)
    private String docUrl;
}
