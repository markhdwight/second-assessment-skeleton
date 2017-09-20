package com.cooksys.Tweeter.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import com.cooksys.Tweeter.entity.TweetContext;

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
		return tweetService.getMentions(username);
	}
	
	@GetMapping("tags/{label}")
	public List<TweetDto> getTaggedTweets(@PathVariable String label)
	{
		return tweetService.getTaggedTweets(label);
	}
	
	@GetMapping("tweets")
	public List<TweetDto> getAllTweets()
	{
		return tweetService.getAll();
	}
	
	@PostMapping("tweets")
	public TweetDto postTweet(@RequestBody String content, @RequestBody Credentials credentials)
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
	
	@GetMapping("tweets/{id}/context")
	public TweetContext getContextOf(@PathVariable Integer id)
	{
		TweetDto start = tweetService.get(id);
		TweetContext context = new TweetContext(start);
		List<TweetDto> before = new ArrayList<TweetDto>();
		List<TweetDto> after = new ArrayList<TweetDto>();
		
		List<TweetDto> tweetList = tweetService.getAllChronological();
		before = getParentTweets(start,tweetList.subList(0,tweetList.indexOf(start)));
		after = getChildTweets(start,tweetList.subList(tweetList.indexOf(start),tweetList.size()));
		
		context.setBefore(before);
		context.setAfter(after);

		return context;
	}
	
	private List<TweetDto> getParentTweets(TweetDto start,List<TweetDto> listSegment)
	{
		List<TweetDto> list = new ArrayList<TweetDto>();
		TweetDto newStart = listSegment.get(0);
		
		if(listSegment.size() == 0)
			return list;
		
		for(int i = listSegment.size()-1; i>=0; i--)
		{
			if(tweetService.areParentChild(listSegment.get(i),start))
			{
				newStart = listSegment.get(i);
				list.add(newStart);
				break;	
			}
		}
		
		List<TweetDto> head = getParentTweets(newStart,listSegment.subList(0, listSegment.indexOf(newStart)-1));
		
		head.addAll(list);
		
		return head;
	}
	
	private List<TweetDto> getChildTweets(TweetDto start,List<TweetDto> listSegment)
	{	
		List<TweetDto> list = new ArrayList<TweetDto>();
		TweetDto newStart = listSegment.get(listSegment.size()-1);
		
		if(listSegment.size() == 0)
			return list;
		
		for(TweetDto t: listSegment)
		{
			if(tweetService.areParentChild(start,t));
			{
				newStart = t;
				list.add(newStart);
				break;
			}
		}
		
		list.addAll(getChildTweets(newStart,listSegment.subList(listSegment.indexOf(newStart)+1, listSegment.size())));
		
		return list;
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
