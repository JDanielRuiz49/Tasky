package com.tasks.task.service;

import com.tasks.task.config.JwtUtil;
import com.tasks.task.model.TokenResponse;
import com.tasks.task.model.User;
import com.tasks.task.model.UserDto;
import com.tasks.task.repository.AuthRepository;
import com.tasks.task.service.exceptions.RequestFormatInvalid;
import com.tasks.task.service.exceptions.UserAlreadyExists;
import com.tasks.task.service.exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public TokenResponse loginUser(UserDto user) {
        User userLog = authRepository.findByUsername(user.getUsername());
        if(userLog == null){
            throw new UserNotFound("User not found");
        }
        if(!passwordEncoder.matches(user.getPassword(), userLog.getPassword())){
            throw new RequestFormatInvalid("Password invalid");
        }
        return new TokenResponse(jwtUtil.generateToken(userLog.getUsername()));
    }

    @Override
    public void registerUser(UserDto user) {

        if (authRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExists("User with username already exists");
        }

        User userPersistence = new User();
        userPersistence.setUsername(user.getUsername());
        userPersistence.setPassword(passwordEncoder.encode(user.getPassword()));

        authRepository.save(userPersistence);
    }
}
