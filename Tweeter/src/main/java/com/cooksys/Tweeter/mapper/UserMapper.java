package com.cooksys.Tweeter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.Tweeter.dto.UserDto;
import com.cooksys.Tweeter.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper 
{
	public User fromDto(UserDto dto);
	public UserDto toDto(User user);
}
