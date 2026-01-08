package com.example.BanCobolApiREST.Services;

import com.example.BanCobolApiREST.DTO.NewUser;
import com.example.BanCobolApiREST.Mapper.UserMapper;
import com.example.BanCobolApiREST.Models.User;
import com.example.BanCobolApiREST.Repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void createNewUser (NewUser newUser){
        newUser.setUserPin(passwordEncoder.encode(newUser.getUserPin()));
        User user = userMapper.toDefaultEntity(newUser);
        userRepository.save(user);
    }
}
