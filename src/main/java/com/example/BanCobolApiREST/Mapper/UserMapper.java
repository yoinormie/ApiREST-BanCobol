package com.example.BanCobolApiREST.Mapper;

import com.example.BanCobolApiREST.DTO.NewUser;
import com.example.BanCobolApiREST.Models.User;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(NewUser newUser);

    default User toDefaultEntity(NewUser newUser){
        User user = toEntity(newUser);
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
