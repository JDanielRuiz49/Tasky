package com.tasks.task.controller;

import com.tasks.task.model.TokenResponse;
import com.tasks.task.model.UserDto;
import com.tasks.task.service.AuthService;
import com.tasks.task.service.exceptions.RequestFormatInvalid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody UserDto user){
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            throw new RequestFormatInvalid("Invalid request format");
        }
        authService.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("Signup successful");
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody UserDto user){
        if(user.getUsername().isEmpty() || user.getPassword().isEmpty()){
            throw new RequestFormatInvalid("Invalid request format");
        }
        TokenResponse tokenResponse = authService.loginUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }
}
