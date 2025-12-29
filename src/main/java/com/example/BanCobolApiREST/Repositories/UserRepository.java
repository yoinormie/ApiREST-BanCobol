package com.example.BanCobolApiREST.Repositories;

import com.example.BanCobolApiREST.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
