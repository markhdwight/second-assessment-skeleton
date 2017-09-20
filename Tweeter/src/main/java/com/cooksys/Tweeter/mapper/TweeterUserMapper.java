package com.cooksys.Tweeter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.Tweeter.dto.TweeterUserDto;
import com.cooksys.Tweeter.entity.TweeterUser;

@Mapper(componentModel = "spring")
public interface TweeterUserMapper 
{
	public TweeterUser fromDto(TweeterUserDto dto);
	public TweeterUserDto toDto(TweeterUser user);
}
