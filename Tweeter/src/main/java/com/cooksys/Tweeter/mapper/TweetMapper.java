package com.cooksys.Tweeter.mapper;

import org.mapstruct.Mapper;

import com.cooksys.Tweeter.dto.TweetDto;
import com.cooksys.Tweeter.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	public Tweet fromDto(TweetDto dto);
	
	public TweetDto toDto(Tweet tweet);
}
