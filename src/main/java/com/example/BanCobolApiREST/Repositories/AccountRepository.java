package com.example.BanCobolApiREST.Repositories;

import com.example.BanCobolApiREST.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT a.balance FROM Account a WHERE a.accountNumber = :accountNumber")
    Optional<BigDecimal> findBalanceByAccountNumber(@Param("accountNumber") String accountNumber);

    @Modifying
    @Query("""
        UPDATE Account a
        SET a.balance = :newBalance
        WHERE a.accountNumber = :accountNumber
    """)
    int updateBalanceByAccountNumber(
            @Param("accountNumber") String accountNumber,
            @Param("newBalance") BigDecimal newBalance
    );
}
