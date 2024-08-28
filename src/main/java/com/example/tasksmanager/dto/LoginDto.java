package com.example.tasksmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for user authentication.
 */
@Data
@Schema(description = "DTO for user authentication")
public class LoginDto {

    /** The email address of the user.*/
    private String email;

    /** The password of the user. */
    private String password;
}
