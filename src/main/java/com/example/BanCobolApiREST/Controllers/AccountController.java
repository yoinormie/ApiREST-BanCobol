package com.example.BanCobolApiREST.Controllers;

import com.example.BanCobolApiREST.DTO.NewAccount;
import com.example.BanCobolApiREST.Services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createNewAccount")
    public ResponseEntity<?> createNewAccount(NewAccount newAccount){
        try{
            accountService.createNewAccount(newAccount);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PatchMapping("/moveBalance")
    public ResponseEntity<?> makeTransfer (BigDecimal amount, String accountNumberToAdd, String accountNumberToSubtract){
        try{
            accountService.subtractBalance(amount, accountNumberToSubtract);
            accountService.addBalance(amount, accountNumberToAdd);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
