package com.example.tasksmanager.mapper;

import com.example.tasksmanager.dto.RegisterDto;
import com.example.tasksmanager.dto.UserResponseDto;
import com.example.tasksmanager.model.RoleEntity;
import com.example.tasksmanager.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Mapper for converting between User-related DTOs and User entities.
 */
@Mapper(componentModel = "spring", imports = {PasswordEncoder.class, RoleEntity.class})
public abstract class UserMapper {


    @Autowired
    protected PasswordEncoder passwordEncoder;


    /**
     * Converts a {@link RegisterDto} to a {@link UserEntity}.
     *
     * @param registerDto the DTO containing the user registration data
     * @return a UserEntity populated with data from the given DTO, including an encoded password
     */
    @Mapping(target = "password", expression = "java(passwordEncoder.encode(registerDto.getPassword()))")
    public abstract UserEntity toUserEntity(RegisterDto registerDto);


    /**
     * Converts a list of {@link UserEntity} to a list of {@link UserResponseDto}.
     *
     * @param userEntities the list of UserEntity objects to be converted
     * @return a list of UserResponseDto populated with data from the given entities
     */
    public abstract List<UserResponseDto> toPageUserResponseDto(List<UserEntity> userEntities);


    /**
     * Converts a {@link UserEntity} to a {@link UserResponseDto}.
     *
     * @param userEntity the UserEntity to be converted
     * @return a UserResponseDto populated with data from the given entity, including the role names
     */
    @Mapping(target = "roles", expression = "java(userEntity.getRoles().stream().map(RoleEntity::getName).toList())")
    public abstract UserResponseDto toUserResponseDto(UserEntity userEntity);

}