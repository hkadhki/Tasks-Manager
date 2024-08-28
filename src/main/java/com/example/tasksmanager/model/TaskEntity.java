package com.example.tasksmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Represents a task entity.
 * Entity is mapped to the "tasks" table in the database.
 */
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class TaskEntity {

    /** Unique identifier for the task */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Title of the task */
    @Column(unique = true, nullable = false)
    private String title;

    /** Description of the task */
    @Column(nullable = false)
    private String description;

    /** The user to whom the task is assigned */
    @ManyToOne
    private UserEntity user;

    /** Deadline for the task */
    @Column(nullable = false)
    private Date date;

}