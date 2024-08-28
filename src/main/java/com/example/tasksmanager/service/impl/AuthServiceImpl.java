package com.example.tasksmanager.service.impl;

import com.example.tasksmanager.dto.LoginDto;
import com.example.tasksmanager.dto.RegisterDto;
import com.example.tasksmanager.exceptions.ErrorInputDataException;
import com.example.tasksmanager.exceptions.ErrorRegisterUserException;
import com.example.tasksmanager.jwt.JwtGenerator;
import com.example.tasksmanager.mapper.UserMapper;
import com.example.tasksmanager.model.RoleEntity;
import com.example.tasksmanager.model.UserEntity;
import com.example.tasksmanager.repository.RoleRepository;
import com.example.tasksmanager.repository.UserRepository;
import com.example.tasksmanager.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of the authentication and registration service.
 * Provides functionality for user registration and authentication.
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtGenerator jwtGenerator;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtGenerator jwtGenerator, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtGenerator = jwtGenerator;
        this.userMapper = userMapper;
    }


    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param loginDto DTO containing the user's email and password.
     * @return JWT token that is used for subsequent requests..
     */
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtGenerator.generateToken(authentication);
    }


    /**
     * Registers a new user.
     * Checks if a user with the given email already exists.
     * If not, saves the new user and assigns the "USER" role to them.
     *
     * @param registerDto DTO containing the user's email, username and password.
     * @throws ErrorRegisterUserException if there is an error while saving the user.
     * @throws ErrorInputDataException     if a user with the given email already exists.
     */
    public void register(RegisterDto registerDto) throws ErrorRegisterUserException, ErrorInputDataException {
        if (userRepository.existsByEmail(registerDto.getUsername())) {
            log.error("This user already exist");
            throw new ErrorInputDataException("This user already exist");
        }

        UserEntity user = userMapper.toUserEntity(registerDto);
        RoleEntity role = roleRepository.findByName("USER").get();
        user.setRoles(List.of(role));

        try {
            userRepository.save(user);
            log.info("Success registered user");
        } catch (Exception e) {
            log.error("Error user register");
            throw new ErrorRegisterUserException("Error user register");
        }
    }
}
