package com.cooksys.Tweeter.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

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
		return hashtagService.exists(label);
	}
	
	@GetMapping("tags")
	public List<HashtagDto> getHashtags()
	{
		return hashtagService.getAll();
	}
	
}
