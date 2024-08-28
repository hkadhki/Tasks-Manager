package com.example.tasksmanager;

import com.example.tasksmanager.dto.RegisterDto;
import com.example.tasksmanager.dto.UserResponseDto;
import com.example.tasksmanager.mapper.UserMapper;
import com.example.tasksmanager.model.RoleEntity;
import com.example.tasksmanager.model.TaskEntity;
import com.example.tasksmanager.model.UserEntity;
import com.example.tasksmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class UserMapperTest {


    @Autowired
    private UserMapper userMapper ;



    @Test
    void toPageUserResponseDtoTest(){
        //given
        RoleEntity role = new RoleEntity(1, "USER");
        List<RoleEntity> roles = Collections.singletonList(role);
        UserEntity user = new UserEntity(1L, "email", "username", "password", roles);

        //when
        UserResponseDto userResponseDto = userMapper.toUserResponseDto(user);

        //then
        Assertions.assertNotNull(userResponseDto);
        Assertions.assertEquals(userResponseDto.getUsername(), user.getUsername());
        Assertions.assertEquals(userResponseDto.getEmail(), user.getEmail());
        Assertions.assertEquals(userResponseDto.getRoles().get(0), role.getName());
    }


    @Test
    void toUserResponseDtoTest(){
        //given
        RoleEntity role = new RoleEntity(1, "USER");
        List<RoleEntity> roles = Collections.singletonList(role);
        UserEntity user = new UserEntity(1L, "email", "username", "password", roles);
        List<UserEntity> users = Collections.singletonList(user);

        //when
        List<UserResponseDto> userResponseDtos = userMapper.toPageUserResponseDto(users);

        //then
        Assertions.assertNotNull(userResponseDtos);
        Assertions.assertEquals(userResponseDtos.get(0).getUsername(), user.getUsername());
        Assertions.assertEquals(userResponseDtos.get(0).getEmail(), user.getEmail());
        Assertions.assertEquals(userResponseDtos.get(0).getRoles().get(0), role.getName());
    }
}
