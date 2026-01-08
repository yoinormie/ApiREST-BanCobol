package com.example.BanCobolApiREST.Controllers;

import com.example.BanCobolApiREST.DTO.NewAccount;
import com.example.BanCobolApiREST.DTO.TransferObject;
import com.example.BanCobolApiREST.Services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/createNewAccount")
    public ResponseEntity<?> createNewAccount(@RequestBody NewAccount newAccount){
        try{
            accountService.createNewAccount(newAccount);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }

    @PatchMapping("/moveBalance")
    public ResponseEntity<?> makeTransfer (@RequestBody TransferObject transferObject){
        try{
            accountService.subtractBalance(transferObject.getAmount(), transferObject.getAccountNumberToSubtract());
            accountService.addBalance(transferObject.getAmount(), transferObject.getAccountNumberToAdd());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
