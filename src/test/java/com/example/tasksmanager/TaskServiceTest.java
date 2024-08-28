package com.example.tasksmanager;

import com.example.tasksmanager.dto.CreateTaskDto;
import com.example.tasksmanager.exceptions.ErrorInputDataException;
import com.example.tasksmanager.mapper.TaskMapper;
import com.example.tasksmanager.model.TaskEntity;
import com.example.tasksmanager.model.UserEntity;
import com.example.tasksmanager.repository.RoleRepository;
import com.example.tasksmanager.repository.TaskRepository;
import com.example.tasksmanager.repository.UserRepository;
import com.example.tasksmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskServiceImpl taskService;


    @Test
    void createIsOkTest() throws Exception {
        //given
        Optional<UserEntity> user = Optional.of(new UserEntity(1L, "user", "user", "user", new ArrayList<>()));
        when(userRepository.findByEmail("user")).thenReturn(user);

        when(taskRepository.existsByTitle("title")).thenReturn(false);

        CreateTaskDto taskRequest = new CreateTaskDto("title", "description", Date.valueOf("2002-02-02"));
        TaskEntity task = new TaskEntity(1, "title", "de", user.get(), Date.valueOf("2002-02-02"));
        when(taskMapper.toTaskEntity(taskRequest)).thenReturn(task);

        //when
        taskService.create(taskRequest,"user");

        //then
        verify(userRepository, times(1)).findByEmail("user");
        verify(taskRepository, times(1)).existsByTitle("title");
    }

    @Test
    void createAlreadyExistTest() throws Exception {
        //given
        CreateTaskDto taskRequest = new CreateTaskDto("title", "description", Date.valueOf("2002-02-02"));
        when(taskRepository.existsByTitle("title")).thenReturn(true);

        //when
        Throwable exception = assertThrowsExactly(ErrorInputDataException.class,
                ()->{taskService.create(taskRequest, "user");} );

        //then
        assertEquals(ErrorInputDataException.class, exception.getClass());
    }

    @Test
    void deleteTaskTest() throws Exception {
        //given
        Optional<UserEntity> user = Optional.of(new UserEntity(1L, "user", "user", "user", new ArrayList<>()));
        when(userRepository.findByEmail("user")).thenReturn(user);

        TaskEntity task = new TaskEntity(1, "title", "de", user.get(), Date.valueOf("2002-02-02"));
        when(taskRepository.findByTitle("title")).thenReturn(Optional.of(task));

        //when
        taskService.delete("title", "user");

        //then
        verify(taskRepository, times(1)).findByTitle("title");
        verify(userRepository, times(1)).findByEmail("user");

    }

    @Test
    void taskNotExistTest() throws Exception{
        //given
        Optional<UserEntity> user = Optional.of(new UserEntity(1L, "user", "user", "user", new ArrayList<>()));
        when(userRepository.findByEmail("user")).thenReturn(user);
        when(taskRepository.findByTitle("title")).thenReturn(Optional.empty());

        //when
        Throwable exception = assertThrowsExactly(ErrorInputDataException.class,
                ()->{taskService.delete("title", "user");} );

        //then
        assertEquals(ErrorInputDataException.class, exception.getClass());
    }

    @Test
    void editTitleTest() throws Exception {
        //given
        Optional<UserEntity> user = Optional.of(new UserEntity(1L, "user", "user", "user", new ArrayList<>()));
        when(userRepository.findByEmail("user")).thenReturn(user);

        TaskEntity task = new TaskEntity(1, "title", "de", user.get(), Date.valueOf("2002-02-02"));
        when(taskRepository.findByTitle("title")).thenReturn(Optional.of(task));
        when(taskRepository.existsByTitle("newTitle")).thenReturn(false);


        //when
        taskService.editTitle("title","newTitle", "user");

        //then
        verify(taskRepository, times(1)).findByTitle("title");
        verify(taskRepository, times(1)).existsByTitle("newTitle");
        verify(userRepository, times(1)).findByEmail("user");
    }

    @Test
    void editDescriptionTest() throws Exception {
        //given
        Optional<UserEntity> user = Optional.of(new UserEntity(1L, "user", "user", "user", new ArrayList<>()));
        when(userRepository.findByEmail("user")).thenReturn(user);

        TaskEntity task = new TaskEntity(1, "title", "de", user.get(), Date.valueOf("2002-02-02"));
        when(taskRepository.findByTitle("title")).thenReturn(Optional.of(task));

        //when
        taskService.editDescription("title","newDes", "user");

        //then
        verify(taskRepository, times(1)).findByTitle("title");
        verify(userRepository, times(1)).findByEmail("user");
    }

    @Test
    void editDateTest() throws Exception {
        //given
        Optional<UserEntity> user = Optional.of(new UserEntity(1L, "user", "user", "user", new ArrayList<>()));
        when(userRepository.findByEmail("user")).thenReturn(user);

        TaskEntity task = new TaskEntity(1, "title", "de", user.get(), Date.valueOf("2002-02-02"));
        when(taskRepository.findByTitle("title")).thenReturn(Optional.of(task));

        //when
        taskService.editDate("title",new Date(100), "user");

        //then
        verify(taskRepository, times(1)).findByTitle("title");
        verify(userRepository, times(1)).findByEmail("user");
    }

    @Test
    void editUserTest() throws Exception {
        //given
        Optional<UserEntity> user = Optional.of(new UserEntity(1L, "user", "user", "user", new ArrayList<>()));
        when(userRepository.findByEmail("user")).thenReturn(user);

        Optional<UserEntity> user2 = Optional.of(new UserEntity(2L, "user2", "user2", "user2", new ArrayList<>()));
        when(userRepository.findByUsername("user2")).thenReturn(user2);

        TaskEntity task = new TaskEntity(1, "title", "de", user.get(), Date.valueOf("2002-02-02"));
        when(taskRepository.findByTitle("title")).thenReturn(Optional.of(task));

        //when
        taskService.editUser("title", "user2", "user");

        //then
        verify(taskRepository, times(1)).findByTitle("title");
        verify(userRepository, times(1)).findByEmail("user");
        verify(userRepository, times(1)).findByUsername("user2");
    }
}
