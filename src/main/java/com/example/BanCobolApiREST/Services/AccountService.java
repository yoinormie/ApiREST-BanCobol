package com.example.BanCobolApiREST.Services;

import com.example.BanCobolApiREST.DTO.NewAccount;
import com.example.BanCobolApiREST.Mapper.AccountMapper;
import com.example.BanCobolApiREST.Models.Account;
import com.example.BanCobolApiREST.Repositories.AccountRepository;
import com.example.BanCobolApiREST.Utils.StringFormatUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final StringFormatUtils stringFormatUtils;
    private final AccountMapper accountMapper;
    @Value("${cobol.bin.path}")
    private String COBOL_EXE_PATH;

    public AccountService(AccountRepository accountRepository, StringFormatUtils stringFormatUtils, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.stringFormatUtils = stringFormatUtils;
        this.accountMapper = accountMapper;
    }


    public void createNewAccount(NewAccount newAccount){
        Account account = accountMapper.toDefaultEntity(newAccount);
        accountRepository.save(account);
    }

    public void addBalance(BigDecimal amount, String accountTarget){
        BigDecimal targetBalance = accountRepository.findBalanceByAccountNumber(accountTarget).orElseThrow(RuntimeException::new);
        String programParameters = stringFormatUtils.formatString(targetBalance) + stringFormatUtils.formatString(amount);

        ProcessBuilder pb = new ProcessBuilder(COBOL_EXE_PATH + "/adding-balance.exe", programParameters);
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();

            String resultado;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                resultado = reader.lines().collect(Collectors.joining()).trim();
            }

            int exitCode = process.waitFor();
            if (exitCode != 0 && !resultado.matches("\\d+")) {
                throw new RuntimeException("Error en COBOL o salida no válida: " + resultado);
            }

            System.out.println("good");
            int accountsAffected = accountRepository.updateBalanceByAccountNumber(accountTarget, stringFormatUtils.formatToNumber(resultado));

            if(accountsAffected == 0){
                throw new RuntimeException("Error al hacer update");
            }

        }catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public void subtractBalance(BigDecimal amount, String accountTarget){
        BigDecimal targetBalance = accountRepository.findBalanceByAccountNumber(accountTarget).orElseThrow(RuntimeException::new);
        String programParameters = stringFormatUtils.formatString(targetBalance) + stringFormatUtils.formatString(amount);

        ProcessBuilder pb = new ProcessBuilder(COBOL_EXE_PATH + "/substract-balance.exe", programParameters);
        pb.redirectErrorStream(true);
        try {
            Process process = pb.start();

            String resultado;
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                resultado = reader.lines().collect(Collectors.joining()).trim();
            }

            int exitCode = process.waitFor();
            if (exitCode != 0 && !resultado.matches("\\d+")) {
                throw new RuntimeException("Error en COBOL o salida no válida: " + resultado);
            }

            System.out.println("good");
            int accountsAffected = accountRepository.updateBalanceByAccountNumber(accountTarget, stringFormatUtils.formatToNumber(resultado));

            if(accountsAffected == 0){
                throw new RuntimeException("Error al hacer update");
            }

        }catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
