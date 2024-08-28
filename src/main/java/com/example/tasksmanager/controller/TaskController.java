package com.example.tasksmanager.controller;


import com.example.tasksmanager.dto.CreateTaskDto;
import com.example.tasksmanager.dto.TaskResponseDto;
import com.example.tasksmanager.dto.UserResponseDto;
import com.example.tasksmanager.exceptions.ErrorCreateTaskException;
import com.example.tasksmanager.exceptions.ErrorDeleteTaskException;
import com.example.tasksmanager.exceptions.ErrorEditTaskException;
import com.example.tasksmanager.service.impl.TaskServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Controller for managing tasks in the Task Manager application.
 */
@RestController
@RequestMapping("/api/task")
@Tag(name = "Task Manager Controller")
public class TaskController {

    private final TaskServiceImpl taskService;

    SecurityContextHolderStrategy strategy =
            SecurityContextHolder.getContextHolderStrategy();

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }



    /**
     * Creates a new task for the authenticated user.
     *
     * @param createTuskDto the data transfer object containing task details
     * @return ResponseEntity with a success message
     * @throws ErrorCreateTaskException if it was not possible to create a task
     */
    @Operation(
            summary = "Create task",
            description = "Creates a task for an authenticated user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Task already exist",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody CreateTaskDto createTuskDto) throws ErrorCreateTaskException {
        String username = strategy.getContext().getAuthentication().getName();
        taskService.create(createTuskDto, username);
        return new ResponseEntity<>("Task created!", HttpStatus.CREATED);
    }


    /**
     * Deletes a task by its title for the authenticated user.
     *
     * @param title the title of the task to be deleted
     * @return a ResponseEntity with a success message
     * @throws ErrorDeleteTaskException if it was not possible to delete a task
     */
    @Operation(
            summary = "Delete task",
            description = "Deletes a task by title",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "This task does not exist",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401"
                    ),
                    @ApiResponse(
                            description = "You do not have permission to change task",
                            responseCode = "403"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @DeleteMapping("/delete/{title}")
    public ResponseEntity<String> delete(@PathVariable String title) throws ErrorDeleteTaskException {
        String username = strategy.getContext().getAuthentication().getName();
        taskService.delete(title,username);
        return new ResponseEntity<>("Task deleted!", HttpStatus.OK);
    }


    /**
     * This method returns a list of tasks, with support for pagination via offset and limit parameters.
     *
     * @param offset the starting point for retrieving tasks (default is 0, minimum is 0)
     * @param limit  the maximum number of tasks to return (default is 20, minimum is 1, maximum is 100)
     * @return a list of {@link TaskResponseDto} objects representing the tasks
     */
    @Operation(
            summary = "Show my tasks",
            description = "Lists all tasks of an authenticated user",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized",
                            responseCode = "401"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @GetMapping(value = "/show/myTasks", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskResponseDto> showMyTusks(@RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
                                             @RequestParam(value = "limit", defaultValue = "20") @Min(1) @Max(100) Integer limit){
        String username = strategy.getContext().getAuthentication().getName();
        return taskService.showMyTasks(username, limit, offset);
    }


    /**
     * Updates the title of an existing task.
     *
     * @param title    the current title of the task
     * @param newTitle the new title to be set
     * @return a ResponseEntity with a success message
     * @throws ErrorEditTaskException if it was not possible to update  a task
     */
    @Operation(
            summary = "Edit task title",
            description = "Changes the task title to a new one if it is not already taken",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "This Task does not exist",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "You do not have permission to change task",
                            responseCode = "403"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @PatchMapping("/edit/{title}/title")
    public ResponseEntity<String> editTitle(@PathVariable String title, @RequestParam String newTitle) throws ErrorEditTaskException {
        String username = strategy.getContext().getAuthentication().getName();
        taskService.editTitle(title,newTitle,username);
        return new ResponseEntity<>("Title changed!", HttpStatus.OK);
    }

    /**
     * Updates the description of an existing task.
     *
     * @param title          the title of the task
     * @param newDescription the new description to be set
     * @return a ResponseEntity with a success message
     * @throws ErrorEditTaskException if it was not possible to update  a task
     */
    @Operation(
            summary = "Edit task description",
            description = "Edit task description",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Incorrect input data",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "You do not have permission to change task",
                            responseCode = "403"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @PatchMapping("/edit/{title}/description")
    public ResponseEntity<String> editDescription(@PathVariable String title, @RequestParam String newDescription) throws ErrorEditTaskException {
        String username = strategy.getContext().getAuthentication().getName();
        taskService.editDescription(title, newDescription, username);
        return new ResponseEntity<>("Description changed!", HttpStatus.OK);
    }

    /**
     * Updates the due date of an existing task.
     *
     * @param title    the title of the task
     * @param newDate  the new deadline to be set
     * @return a ResponseEntity with a success message
     * @throws ErrorEditTaskException if it was not possible to update  a task
     */
    @Operation(
            summary = "Edit task date",
            description = "Edit task date",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Incorrect input data",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "You do not have permission to change task",
                            responseCode = "403"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @PatchMapping("/edit/{title}/Date")
    public ResponseEntity<String> editDate(@PathVariable String title, @RequestParam("Date") @DateTimeFormat(pattern="yyyy-MM-dd") @Parameter(description = "New deadline for the task in the format yyyy-MM-dd.")Date newDate) throws ErrorEditTaskException {
        String username = strategy.getContext().getAuthentication().getName();
        taskService.editDate(title, new java.sql.Date(newDate.getTime()),username);
        return new ResponseEntity<>("Date changed!", HttpStatus.OK);
    }


    /**
     * Updates the assigned user of an existing task.
     *
     * @param title   the title of the task
     * @param newUser username to whom the task will be assigned
     * @return a ResponseEntity with a success message
     * @throws ErrorEditTaskException if it was not possible to update  a task
     */
    @Operation(
            summary = "Edit task executor",
            description = "Edit the task executor to a new one if it exists",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Incorrect input data",
                            responseCode = "400"
                    ),
                    @ApiResponse(
                            description = "You do not have permission to change task",
                            responseCode = "403"
                    )
            }
    )
    @SecurityRequirement(name = "Bearer Auth")
    @PatchMapping("/edit/{title}/User")
    public ResponseEntity<String> editUser(@PathVariable String title, @RequestParam String newUser) throws ErrorEditTaskException {
        String username = strategy.getContext().getAuthentication().getName();
        taskService.editUser(title, newUser,username);
        return new ResponseEntity<>("User changed!", HttpStatus.OK);
    }


}
