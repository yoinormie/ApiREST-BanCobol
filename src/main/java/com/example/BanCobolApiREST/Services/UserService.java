package com.example.BanCobolApiREST.Services;

import com.example.BanCobolApiREST.Configuration.JwtTokenProvider;
import com.example.BanCobolApiREST.DTO.LoginRequest;
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
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public void createNewUser (NewUser newUser){
        newUser.setUserPin(passwordEncoder.encode(newUser.getUserPin()));
        User user = userMapper.toDefaultEntity(newUser);
        userRepository.save(user);
    }

    public String newLogin(LoginRequest loginRequest){
        User user = userRepository.findUserByIdentifier(loginRequest.getIdentifier()).orElseThrow(RuntimeException::new);
        if(!passwordEncoder.matches(loginRequest.getUserPin(), user.getUserPin())){
            throw new RuntimeException();
        }
        return jwtTokenProvider.generateToken(user.getUserId());
    }
}
