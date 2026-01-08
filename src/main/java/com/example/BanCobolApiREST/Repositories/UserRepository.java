package com.example.BanCobolApiREST.Repositories;

import com.example.BanCobolApiREST.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("""
        SELECT u
        FROM User u
        WHERE u.identifier = :identifier
    """)
    Optional<User> findUserByIdentifier(
            @Param("identifier") String identifier
    );
}
