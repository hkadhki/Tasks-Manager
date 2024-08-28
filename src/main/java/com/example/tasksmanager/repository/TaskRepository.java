package com.example.tasksmanager.repository;

import com.example.tasksmanager.model.TaskEntity;
import com.example.tasksmanager.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for {@link TaskEntity}.
 */
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    /**
     * Deletes a task by its title.
     *
     * @param title The title of the task to delete.
     */
    void deleteByTitle(String title);

    /**
     * Finds a task by its title.
     *
     * @param title The title of the task to find.
     * @return An {@link Optional} containing the {@link TaskEntity} if found, otherwise an empty {@link Optional}.
     */
    Optional<TaskEntity> findByTitle(String title);

    /**
     * Finds tasks assigned to a specific user with pagination.
     *
     * @param user     The {@link UserEntity} whose tasks are to be retrieved.
     * @param pageable The {@link Pageable} object specifying the page request details.
     * @return A {@link Page} containing {@link TaskEntity} assigned to the specified user.
     */
    Page<TaskEntity> findByUser(UserEntity user, Pageable pageable);


    /**
     * Checks if a task with the specified title exists.
     *
     * @param title The title of the task to check.
     * @return {@code true} if a task with the given title exists, otherwise {@code false}.
     */
    Boolean existsByTitle(String title);

}
