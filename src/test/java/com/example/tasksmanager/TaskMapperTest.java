package com.example.tasksmanager;

import com.example.tasksmanager.dto.CreateTaskDto;
import com.example.tasksmanager.dto.TaskResponseDto;
import com.example.tasksmanager.mapper.TaskMapper;
import com.example.tasksmanager.model.TaskEntity;
import com.example.tasksmanager.model.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class TaskMapperTest {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    void toTaskEntityTest(){
        //given
        CreateTaskDto createTaskDto = new CreateTaskDto("title", "des", Date.valueOf("2000-02-02"));

        //when
        TaskEntity task = taskMapper.toTaskEntity(createTaskDto);

        //then
        Assertions.assertNotNull(task);
        Assertions.assertEquals(task.getTitle(), createTaskDto.getTitle());
        Assertions.assertEquals(task.getDescription(), createTaskDto.getDescription());
        Assertions.assertEquals(task.getDate(), createTaskDto.getDate());

    }

    @Test
    void toListTaskResponseDtoTest(){
        //given
        TaskEntity task = new TaskEntity(1,"title", "des", new UserEntity(), Date.valueOf("2000-02-02"));
        List<TaskEntity> list = Collections.singletonList(task);

        //when
        List<TaskResponseDto> test = taskMapper.toListTaskResponseDto(list);

        //then
        Assertions.assertNotNull(test);
        Assertions.assertEquals(test.get(0).getTitle(), task.getTitle());
        Assertions.assertEquals(test.get(0).getDescription(), task.getDescription());
        Assertions.assertEquals(test.get(0).getDate(), task.getDate());
    }
}
