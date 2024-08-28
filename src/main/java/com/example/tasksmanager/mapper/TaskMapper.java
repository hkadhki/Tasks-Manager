package com.example.tasksmanager.mapper;

import com.example.tasksmanager.dto.CreateTaskDto;
import com.example.tasksmanager.dto.RegisterDto;
import com.example.tasksmanager.dto.TaskResponseDto;
import com.example.tasksmanager.dto.UserResponseDto;
import com.example.tasksmanager.model.TaskEntity;
import com.example.tasksmanager.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Mapper interface for converting between Task-related DTOs and Task entities.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {


    /**
     * Converts a {@link CreateTaskDto} to a {@link TaskEntity}.
     *
     * @param createTaskDto the DTO containing the task creation data
     * @return a TaskEntity populated with data from the given DTO
     */
    TaskEntity toTaskEntity(CreateTaskDto createTaskDto);

    /**
     * Converts a list of {@link TaskEntity} to a list of {@link TaskResponseDto}.
     *
     * @param taskEntities the list of TaskEntity objects to be converted
     * @return a list of TaskResponseDto populated with data from the given entities
     */
    List<TaskResponseDto> toListTaskResponseDto(List<TaskEntity> taskEntities);
}
