package com.cooksys.Tweeter.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.Tweeter.dto.TweetDto;
import com.cooksys.Tweeter.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	public Tweet fromDto(TweetDto dto);
	
	public TweetDto toDto(Tweet tweet);
	
	public List<TweetDto> toDtos(List<Tweet> tweets);
}
