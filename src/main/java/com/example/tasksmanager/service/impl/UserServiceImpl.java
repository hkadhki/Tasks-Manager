package com.example.tasksmanager.service.impl;

import com.example.tasksmanager.dto.UserResponseDto;
import com.example.tasksmanager.mapper.UserMapper;
import com.example.tasksmanager.model.UserEntity;
import com.example.tasksmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the user service providing operations related to users.
 */
@Service
@Slf4j
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Retrieves a paginated list of users and converts it to a list of response DTOs.
     *
     * @param limit  The maximum number of users to retrieve per page.
     * @param offset The page number to retrieve (zero-based index).
     * @return A list of {@link UserResponseDto} containing user information.
     */
    public List<UserResponseDto> showAllUsers(Integer limit, Integer offset){
        Page<UserEntity> page = userRepository.findAll(PageRequest.of(offset,limit));
        return userMapper.toPageUserResponseDto(page.getContent());
    }
}
