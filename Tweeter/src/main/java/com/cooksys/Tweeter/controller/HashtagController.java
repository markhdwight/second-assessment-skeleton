package com.cooksys.Tweeter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.Tweeter.dto.HashtagDto;
import com.cooksys.Tweeter.service.HashtagService;

@RestController
@RequestMapping("hashtag")
public class HashtagController 
{
	private HashtagService hashtagService;
	
	public HashtagController(HashtagService hashtagService)
	{
		this.hashtagService = hashtagService;
	}
	
	@GetMapping("validate/tag/exists/{label}")
	public boolean hashtagExists(@PathVariable String label)
	{
		return false;
	}
	
	@GetMapping("tags")
	public List<HashtagDto> getHashtags()
	{
		return null;
	}
	
	@GetMapping("tags/{label}")
	public HashtagDto getHashtag(@PathVariable String label)
	{
		return null;
	}
	
	@GetMapping("tweets/{id}/tags")
	public List<HashtagDto> getHashtagsInMessage(@PathVariable Integer id)
	{
		return null;
	}
}
