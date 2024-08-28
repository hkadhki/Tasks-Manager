package com.example.tasksmanager.controller;

import com.example.tasksmanager.dto.LoginDto;
import com.example.tasksmanager.dto.RegisterDto;
import com.example.tasksmanager.exceptions.ErrorInputDataException;
import com.example.tasksmanager.exceptions.ErrorRegisterUserException;
import com.example.tasksmanager.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling authentication and registration requests.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication Controller")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService) {
        this.authService = authService;
    }

    /**
     * This method authenticates a user based on the provided credentials and returns a JWT token.
     *
     * @param loginDto the DTO containing the user's login credentials
     * @return a ResponseEntity containing the JWT token and HTTP status 200 if authentication is successful
     */
    @Operation(
            summary = "Authorization",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401"
                    )
            }
    )
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        String jwtToken = authService.login(loginDto);
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    /**
     * This method registers a new user in the system with the provided registration details.
     *
     * @param registerDto the DTO containing the user's registration details
     * @return a ResponseEntity containing a success message and HTTP status 200 if registration is successful
     * @throws ErrorRegisterUserException if there is an error saving
     * @throws ErrorInputDataException if the input data is invalid
     */
    @Operation(
            summary = "Registration",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "This user already exist",
                            responseCode = "400"
                    )
            }
    )
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) throws ErrorRegisterUserException, ErrorInputDataException {
        authService.register(registerDto);
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
