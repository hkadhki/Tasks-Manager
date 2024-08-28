package com.example.tasksmanager.controller;

import com.example.tasksmanager.dto.UserResponseDto;
import com.example.tasksmanager.service.UserService;
import com.example.tasksmanager.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling user-related operations.
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }


    /**
     * This method returns a list of all users, with support for pagination via offset and limit parameters.
     *
     * @param offset the starting point for retrieving users (default is 0, minimum is 0)
     * @param limit  the maximum number of users to return (default is 20, minimum is 1, maximum is 100)
     * @return a list of {@link UserResponseDto} objects representing the users
     */
    @Operation(
            summary = "Show all users",
            description = "Returns a list of all users, with support for pagination",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
            }
    )
    @GetMapping(value = "/showAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponseDto> showAllUsers(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                              @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit){
        return userService.showAllUsers(limit,offset);
    }
}
