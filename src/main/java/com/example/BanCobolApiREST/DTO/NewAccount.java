package com.example.BanCobolApiREST.DTO;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NewAccount {
    private BigDecimal balance;
    private String accountType;
    private Long userId;
}
