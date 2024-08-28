package com.example.tasksmanager.service;

import com.example.tasksmanager.dto.CreateTaskDto;
import com.example.tasksmanager.dto.TaskResponseDto;
import com.example.tasksmanager.dto.UserResponseDto;
import com.example.tasksmanager.exceptions.ErrorCreateTaskException;
import com.example.tasksmanager.exceptions.ErrorDeleteTaskException;
import com.example.tasksmanager.exceptions.ErrorEditTaskException;

import java.sql.Date;
import java.util.List;

public interface TaskService {
    void create(CreateTaskDto createTuskDto, String username) throws ErrorCreateTaskException;
    void delete(String title, String username) throws ErrorDeleteTaskException;
    List<TaskResponseDto> showMyTasks(String username, Integer limit, Integer offset);
    void editTitle(String title, String newTitle, String username) throws ErrorEditTaskException;

    void editDescription(String title, String newDescription, String username) throws ErrorEditTaskException;
    void editDate(String title, Date newDate, String username) throws ErrorEditTaskException;
    void editUser(String title, String newUser, String username) throws ErrorEditTaskException;



}
