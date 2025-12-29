package com.example.BanCobolApiREST.Repositories;

import com.example.BanCobolApiREST.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
