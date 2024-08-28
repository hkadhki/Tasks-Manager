package com.example.tasksmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

/**
 * DTO for creating a task .
 */
@Data
@AllArgsConstructor
@Schema(description = "DTO for creating a task")
public class CreateTaskDto {
    /** The title of the task. */
    private String title;

    /** A brief description of the task. */
    private String description;

    /** Deadline for the task in the format yyyy-MM-dd */
    @Schema(description = "Deadline for the task in the format yyyy-MM-dd")
    private Date date;
}
