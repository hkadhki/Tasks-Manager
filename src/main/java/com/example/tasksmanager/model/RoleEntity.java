package com.example.tasksmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a role entity .
 * Entity is mapped to the "roles" table in the database.
 */
@Getter
@Setter
@Table(name = "roles")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {

    /** Unique identifier for the role */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** The name of the role */
    private String name;

}