package com.example.tasksmanager.service;

import com.example.tasksmanager.dto.LoginDto;
import com.example.tasksmanager.dto.RegisterDto;
import com.example.tasksmanager.exceptions.ErrorInputDataException;
import com.example.tasksmanager.exceptions.ErrorRegisterUserException;

public interface AuthService {
    String login(LoginDto loginDto);
    void register(RegisterDto registerDto) throws ErrorRegisterUserException, ErrorInputDataException;
}
