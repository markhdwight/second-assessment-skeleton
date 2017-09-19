package com.cooksys.Tweeter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.Tweeter.dto.HashtagDto;
import com.cooksys.Tweeter.entity.Hashtag;

@Mapper(componentModel = "spring")
public interface HashtagMapper {
	
	public Hashtag fromDto(HashtagDto dto);
	
	public HashtagDto toDto(Hashtag hashtag);

}
