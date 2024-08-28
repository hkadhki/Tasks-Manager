package com.example.tasksmanager.service.impl;

import com.example.tasksmanager.dto.CreateTaskDto;
import com.example.tasksmanager.dto.TaskResponseDto;
import com.example.tasksmanager.dto.UserResponseDto;
import com.example.tasksmanager.exceptions.*;
import com.example.tasksmanager.mapper.TaskMapper;
import com.example.tasksmanager.mapper.UserMapper;
import com.example.tasksmanager.model.RoleEntity;
import com.example.tasksmanager.model.TaskEntity;
import com.example.tasksmanager.model.UserEntity;
import com.example.tasksmanager.repository.TaskRepository;
import com.example.tasksmanager.repository.UserRepository;
import com.example.tasksmanager.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the task service providing operations related to tasks.
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, UserMapper userMapper, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.taskMapper = taskMapper;
    }


    /**
     * Creates a new task and assigns it to the specified user.
     * Checks if a task with the same title already exists. If not, the task is saved and assigned to the user.
     *
     * @param createTaskDto DTO containing the task details.
     * @param username      The username of the user creating the task.
     * @throws ErrorCreateTaskException if there is an error creating the task.
     * @throws ErrorInputDataException  if a task with the given title already exists.
     */
    @Transactional
    public void create(CreateTaskDto createTaskDto, String username) throws ErrorCreateTaskException {

        if (taskRepository.existsByTitle(createTaskDto.getTitle())) {
            log.error("Task '{}' already exist", createTaskDto.getTitle());
            throw new ErrorInputDataException("Task "+ createTaskDto.getTitle() +" already exist");
        }

        UserEntity user = findUserByEmail(username);
        TaskEntity task = taskMapper.toTaskEntity(createTaskDto);
        task.setUser(user);

        taskRepository.save(task);
        log.info("Success create Task '{}' for user '{}'", createTaskDto.getTitle(), username);
    }

    /**
     * Deletes a task with the specified title if the user has permission.
     *
     * @param title    The title of the task to delete.
     * @param username The username of the user requesting the deletion.
     * @throws ErrorDeleteTaskException if there is an error deleting the task.
     * @throws ErrorPermissionException if the user does not have permission to delete the task.
     */
    @Transactional
    public void delete(String title, String username) throws ErrorDeleteTaskException {
        TaskEntity task = getTaskIfUserHasPermission(title, username);
        taskRepository.delete(task);
        log.info("Success delete Task '{}' for user '{}'", title, username);
    }

    /**
     * Retrieves a paginated list of tasks assigned to the specified user.
     *
     * @param username The username of the user whose tasks are being retrieved.
     * @param limit    The maximum number of tasks to retrieve per page.
     * @param offset   The page number to retrieve (zero-based index).
     * @return A list of {@link TaskResponseDto} containing task information.
     */
    public List<TaskResponseDto> showMyTasks(String username, Integer limit, Integer offset) {
        UserEntity user = findUserByEmail(username);
        Page<TaskEntity> page = taskRepository.findByUser(user, PageRequest.of(offset,limit));

        return taskMapper.toListTaskResponseDto(page.getContent());
    }

    /**
     * Updates the title of a task if the user has permission and the new title is not taken.
     *
     * @param title     The current title of the task to update.
     * @param newTitle  The new title for the task.
     * @param username  The username of the user requesting the update.
     * @throws ErrorEditTaskException  if there is an error updating the task.
     * @throws ErrorInputDataException if the new title is already taken.
     * @throws ErrorPermissionException if the user does not have permission to edit the task.
     */
    @Transactional
    public void editTitle(String title, String newTitle, String username) throws ErrorEditTaskException {
        if(taskRepository.existsByTitle(newTitle)){
            log.error("Title '" + newTitle + "'is already taken");
            throw new ErrorInputDataException("Title '" + newTitle + "'is already taken");
        }

        TaskEntity task = getTaskIfUserHasPermission(title, username);

        task.setTitle(newTitle);
        taskRepository.save(task);
        log.info("Successfully edited task '{}' for user '{}'", title, username);
    }

    /**
     * Updates the description of a task if the user has permission.
     *
     * @param title        The title of the task to update.
     * @param newDescription The new description for the task.
     * @param username     The username of the user requesting the update.
     * @throws ErrorEditTaskException if there is an error updating the task.
     * @throws ErrorPermissionException if the user does not have permission to edit the task.
     */
    @Transactional
    public void editDescription(String title, String newDescription, String username) throws ErrorEditTaskException {
        TaskEntity task = getTaskIfUserHasPermission(title, username);
        task.setDescription(newDescription);
        taskRepository.save(task);
        log.info("Successfully edited task '{}' for user '{}'", title, username);
    }

    /**
     * Updates the date of a task if the user has permission.
     *
     * @param title       The title of the task to update.
     * @param newDate     The new date for the task.
     * @param username    The username of the user requesting the update.
     * @throws ErrorEditTaskException if there is an error updating the task.
     * @throws ErrorPermissionException if the user does not have permission to edit the task.
     */
    @Transactional
    public void editDate(String title, Date newDate, String username) throws ErrorEditTaskException {
        TaskEntity task = getTaskIfUserHasPermission(title, username);
        task.setDate(newDate);
        taskRepository.save(task);
        log.info("Successfully edited task '{}' for user '{}'", title, username);
    }


    /**
     * Changes the user assigned to a task if the current user has permission.
     *
     * @param title      The title of the task to update.
     * @param newUser    The username of the new user to assign to the task.
     * @param username   The username of the current user requesting the change.
     * @throws ErrorEditTaskException  if there is an error updating the task.
     * @throws ErrorInputDataException if the new user does not exist.
     * @throws ErrorPermissionException if the user does not have permission to edit the task.
     */
    @Transactional
    public void editUser(String title, String newUser, String username) throws ErrorEditTaskException {
        TaskEntity task = getTaskIfUserHasPermission(title, username);

        UserEntity newUserEntity = userRepository.findByUsername(newUser)
                .orElseThrow(() -> new ErrorInputDataException("User '" + newUser + "' does not exist"));

        task.setUser(newUserEntity);
        taskRepository.save(task);
        log.info("Successfully edited task '{}' for user '{}'", title, username);
    }

    /**
     * Retrieves a task by title and checks if the current user has permission to modify it.
     *
     * @param title    The title of the task to retrieve.
     * @param username The username of the user requesting the task.
     * @return The {@link TaskEntity} if the user has permission to modify it.
     * @throws ErrorPermissionException if the user does not have permission to modify the task.
     */
    private TaskEntity getTaskIfUserHasPermission(String title, String username) {
        UserEntity user = findUserByEmail(username);
        TaskEntity taskEntity = findTaskByTitle(title);

        if (!taskEntity.getUser().equals(user)) {
            log.error("User '{}' does not have permission to modify task '{}'", username, title);
            throw new ErrorPermissionException("You do not have permission to change task");
        }

        return taskEntity;
    }

    /**
     * Finds a user by their email address.
     *
     * @param email The email address of the user to find.
     * @return The {@link UserEntity} corresponding to the provided email address.
     * @throws UnauthorizedErrorException if no user with the given email is found.
     */
    private UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedErrorException("User with email '" + email + "' not found"));
    }

    /**
     * Finds a task by its title.
     *
     * @param title The title of the task to find.
     * @return The {@link TaskEntity} corresponding to the provided title.
     * @throws ErrorInputDataException if no task with the given title is found.
     */
    private TaskEntity findTaskByTitle(String title) {
        return taskRepository.findByTitle(title)
                .orElseThrow(() -> new ErrorInputDataException("Task with title '" + title + "' not found"));
    }

}
