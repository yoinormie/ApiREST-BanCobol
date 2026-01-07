package com.example.BanCobolApiREST.Services;

import com.example.BanCobolApiREST.DTO.NewUser;
import com.example.BanCobolApiREST.Mapper.UserMapper;
import com.example.BanCobolApiREST.Models.User;
import com.example.BanCobolApiREST.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public void createNewUser (NewUser newUser){
        User user = userMapper.toDefaultEntity(newUser);
        userRepository.save(user);
    }
}
