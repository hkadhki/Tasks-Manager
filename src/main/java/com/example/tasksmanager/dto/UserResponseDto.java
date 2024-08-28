package com.example.tasksmanager.dto;

import com.example.tasksmanager.model.RoleEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO for representing user
 */
@Data
@AllArgsConstructor
@Schema(description = "DTO for representing user")
public class UserResponseDto {

    /** The email of the user.*/
    private String email;

    /** The username of the user. */
    private String username;

    /** The list of roles assigned to the user. */
    private List<String> roles;
}
