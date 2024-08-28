package com.example.tasksmanager.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a user entity.
 * Entity is mapped to the "users" table in the database.
 */
@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    /** Unique identifier for the user  */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Email address of the user */
    @Column(unique = true, nullable = false)
    private String email;

    /** Username of the user  */
    @Column(unique = true, nullable = false)
    private String username;

    /** Password of the user */
    @Column(nullable = false)
    private String password;


    /** Roles assigned to the user */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<RoleEntity> roles = new ArrayList<>();


}
