package com.example.BanCobolApiREST.Models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "identifier", nullable = false, unique = true, length = 255)
    private String identifier;

    @Column(name = "user_pin", nullable = false, length = 255)
    private String userPin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Account> accounts;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}