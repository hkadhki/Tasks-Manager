package com.example.tasksmanager.service;

import com.example.tasksmanager.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> showAllUsers(Integer limit, Integer offset);
}
