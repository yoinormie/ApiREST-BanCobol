package com.example.BanCobolApiREST.Controllers;

import com.example.BanCobolApiREST.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/test")
    public ResponseEntity<?> test (@RequestParam Integer n1, @RequestParam Integer n2){
        return ResponseEntity.ok(userService.ejecutarSuma(n1, n2));
    }
}
