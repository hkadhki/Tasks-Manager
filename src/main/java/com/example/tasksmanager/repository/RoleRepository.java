package com.example.tasksmanager.repository;

import com.example.tasksmanager.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for {@link RoleEntity}.
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Finds a role by its name.
     *
     * @param name The name of the role to find.
     * @return An {@link Optional} containing the {@link RoleEntity} if found, otherwise an empty {@link Optional}.
     */
    Optional<RoleEntity> findByName(String name);
}
