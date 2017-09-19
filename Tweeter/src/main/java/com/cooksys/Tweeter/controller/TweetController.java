package com.cooksys.Tweeter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.Tweeter.dto.TweetDto;
import com.cooksys.Tweeter.service.TweetService;
import com.cooksys.Tweeter.entity.Credentials;

@RestController
@RequestMapping("tweet")
public class TweetController {

	private TweetService tweetService;
	
	public TweetController(TweetService tweetService)
	{
		this.tweetService = tweetService;
	}
	
	@GetMapping("users/@{username}/feed")
	public List<TweetDto> getFeed(@PathVariable String username)
	{
		return null;
	}
	
	@GetMapping("users/@{username}/tweets")
	public List<TweetDto> getTweets(@PathVariable String username)
	{
		return null;
	}
	
	@GetMapping("users/@{username}/mentions")
	public List<TweetDto> getMentions(@PathVariable String username)
	{
		return null;
	}
	
	@GetMapping("tags/{label}")
	public List<TweetDto> getTaggedTweets(@PathVariable String label)
	{
		return null;
	}
	
	@GetMapping
	public List<TweetDto> getAllTweets()
	{
		return tweetService.getAll();
	}
	
	@PostMapping
	public TweetDto postTweet(@RequestBody TweetDto tweet)
	{
		return null;
	}
	
	@GetMapping("tweets/{id}")
	public TweetDto getTweetById(@PathVariable Integer id)
	{
		return null;
	}
	
	@DeleteMapping("tweets/{id}")
	public TweetDto removeTweetById(@RequestBody Credentials credentials,@PathVariable Integer id)
	{
		return null;
	}
	
	@PostMapping("tweets/{id}/like")
	public void likeTweetById(@RequestBody Credentials credentials, @PathVariable Integer id)
	{
		
	}
	
	@PostMapping("tweets/{id}/reply")
	public TweetDto replyById(@RequestBody Credentials credentials, @PathVariable Integer id)
	{
		return null;
	}
	
	@PostMapping("tweets/{id}/repost")
	public TweetDto repostById(@RequestBody Credentials credentials, @PathVariable Integer id)
	{
		return null;
	}
	
	@GetMapping("tweets/{id}/replies")
	public List<TweetDto> getDirectReplies(@PathVariable Integer id)
	{
		return null;
	}
	
	@GetMapping("tweets/{id}/reposts")
	public List<TweetDto> getDirectReposts(@PathVariable Integer id)
	{
		return null;
	}
}
