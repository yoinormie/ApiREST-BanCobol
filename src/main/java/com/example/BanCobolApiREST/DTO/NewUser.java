package com.example.BanCobolApiREST.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewUser {
    private String identifier;
    private String userPin;
    private LocalDateTime createdAt;
}
