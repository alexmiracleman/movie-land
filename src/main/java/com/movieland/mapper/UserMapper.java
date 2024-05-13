package com.movieland.mapper;

import com.movieland.dto.UserDto;
import com.movieland.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserDto toUserDto(User entity);
}
