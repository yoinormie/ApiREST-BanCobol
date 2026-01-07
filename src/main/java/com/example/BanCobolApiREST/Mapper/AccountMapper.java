package com.example.BanCobolApiREST.Mapper;

import com.example.BanCobolApiREST.DTO.NewAccount;
import com.example.BanCobolApiREST.Models.Account;
import com.example.BanCobolApiREST.Models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toEntity (NewAccount newAccount);

    default Account toDefaultEntity (NewAccount newAccount){
        Account account = toEntity(newAccount);
        User user = new User();
        user.setUserId(newAccount.getUserId());
        account.setUser(user);
        return account;
    }
}
