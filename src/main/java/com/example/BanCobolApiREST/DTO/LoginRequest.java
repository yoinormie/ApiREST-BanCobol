package com.example.BanCobolApiREST.DTO;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String identifier;
    private String userPin;
}
