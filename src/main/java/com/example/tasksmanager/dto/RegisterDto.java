package com.example.tasksmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for user registration.
 */
@Data
@AllArgsConstructor
@Schema(description = "DTO for user registration")
public class RegisterDto {
    /** The username chosen by the user. */
    private String email;

    /** The username chosen by the user */
    private String username;

    /** The password chosen by the user */
    private String password;
}
