package com.tasks.task.service;

import com.tasks.task.model.TokenResponse;
import com.tasks.task.model.UserDto;

public interface AuthService {

    TokenResponse loginUser(UserDto user);

    void registerUser(UserDto user);


}
