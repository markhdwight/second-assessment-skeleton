package com.cooksys.Tweeter.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.Tweeter.dto.HashtagDto;
import com.cooksys.Tweeter.dto.TweetDto;
import com.cooksys.Tweeter.service.HashtagService;
import com.cooksys.Tweeter.service.TweetService;
import com.cooksys.Tweeter.service.TweeterUserService;
import com.cooksys.Tweeter.entity.Credentials;
import com.cooksys.Tweeter.entity.TweetContext;
import com.cooksys.Tweeter.entity.TweetInfo;

@RestController
@RequestMapping("tweet")
public class TweetController {

	private TweetService tweetService;
	private TweeterUserService userService;
	private HashtagService hashtagService;
	
	public TweetController(TweetService tweetService,TweeterUserService userService,HashtagService hashtagService)
	{
		this.tweetService = tweetService;
		this.userService = userService;
		this.hashtagService = hashtagService;
	}
	
	@GetMapping("users/@{username}/feed")
	public List<TweetDto> getFeed(@PathVariable String username, HttpServletResponse response)
	{
		if(userService.exists(username))
		{
			List<TweetDto> tweets = tweetService.getFeedFor(username);
			response.setStatus(HttpServletResponse.SC_FOUND);
			return tweets;
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
	
	@GetMapping("users/@{username}/tweets")
	public List<TweetDto> getTweets(@PathVariable String username, HttpServletResponse response)
	{
		List<TweetDto> tweets = tweetService.getTweetsBy(username);
		
		if(!userService.exists(username))
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		else response.setStatus(HttpServletResponse.SC_FOUND);
		
		return tweets;
	}
	
	@GetMapping("users/@{username}/mentions")
	public List<TweetDto> getMentions(@PathVariable String username)
	{
		return tweetService.getMentions(username);
	}
	
	@GetMapping("tweets")
	public List<TweetDto> getAllTweets()
	{
		return tweetService.getAll();
	}
	
	@PostMapping
	public TweetDto postTweet(@RequestBody TweetInfo info,HttpServletResponse response)
	{
		String content = info.content;
		Credentials credentials = info.credentials;
		
		int id = userService.verifyUser(credentials.getUsername(), credentials.getPassword());
		
		if(id > 0 && userService.isActiveUser(userService.get(id)))
		{
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return tweetService.create(content,credentials.getUsername());
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return null;
	}
	
	@GetMapping("tweets/{id}")
	public TweetDto getTweetById(@PathVariable Integer id,HttpServletResponse response)
	{
		TweetDto tweet = tweetService.get(id);
		
		if(!(tweet == null) && tweet.isActive())
			response.setStatus(HttpServletResponse.SC_FOUND);
		else response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
		return tweet;
	}
	
	@DeleteMapping("tweets/{id}")
	public TweetDto removeTweetById(@RequestBody Credentials credentials,@PathVariable Integer id,HttpServletResponse response)
	{
		int userId = userService.verifyUser(credentials.getUsername(),credentials.getPassword());
		
		if(userId > 0)
		{
			TweetDto tweet = tweetService.get(id);
			
			if(tweet == null)
			{
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				return tweet;
			}
			
			response.setStatus(HttpServletResponse.SC_FOUND);
			return tweetService.deactivate(id);
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return null;
	}
	
	@PostMapping("tweets/{id}/like")
	public void likeTweetById(@RequestBody Credentials credentials, @PathVariable Integer id, HttpServletResponse response)
	{
		int userId = userService.verifyUser(credentials.getUsername(),credentials.getPassword());
		
		if(userId > 0 && tweetService.exists(id))
		{
			tweetService.addToLikes(id,userId);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}
		else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	@PostMapping("tweets/{id}/reply")
	public TweetDto replyById(@RequestBody TweetInfo info, @PathVariable Integer id, HttpServletResponse response)
	{
		String content = info.content;
		Credentials credentials = info.credentials;
		
		int userId = userService.verifyUser(credentials.getUsername(),credentials.getPassword());
		
		if(userId > 0 && !content.isEmpty())
		{
			TweetDto reply = tweetService.createReply(credentials.getUsername(),id,content);
			
			if(reply == null)
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			else response.setStatus(HttpServletResponse.SC_FOUND);
			
			return reply;
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return null;
	}
	
	@PostMapping("tweets/{id}/repost")
	public TweetDto repostById(@RequestBody Credentials credentials, @PathVariable Integer id, HttpServletResponse response)
	{
		int userId = userService.verifyUser(credentials.getUsername(), credentials.getPassword());
		
		if(userId > 0)
		{
			TweetDto repost = tweetService.createRepost(credentials.getUsername(),id);
			
			if(repost == null)
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			else response.setStatus(HttpServletResponse.SC_FOUND);
			
			return repost;
		}
		
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return null;
	}
	
	@GetMapping("tweets/{id}/context")
	public TweetContext getContextOf(@PathVariable Integer id, HttpServletResponse response)
	{
		if(tweetService.exists(id))
		{
			TweetContext context = tweetService.getContext(id);
			response.setStatus(HttpServletResponse.SC_FOUND);
			return context;
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
	
	@GetMapping("tweets/{id}/replies")
	public List<TweetDto> getDirectReplies(@PathVariable Integer id, HttpServletResponse response)
	{
		List<TweetDto> replies = tweetService.getRepliesOf(id);
		
		if(replies == null)
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		else response.setStatus(HttpServletResponse.SC_FOUND);
		
		return replies;
	}
	
	@GetMapping("tweets/{id}/reposts")
	public List<TweetDto> getDirectReposts(@PathVariable Integer id, HttpServletResponse response)
	{
		List<TweetDto> reposts = tweetService.getRepostsOf(id);
		
		if(reposts == null)
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		else response.setStatus(HttpServletResponse.SC_FOUND);
		
		return reposts;
	}
	
	@GetMapping("tags/{label}")
	public List<TweetDto> getTweetsTagged(@PathVariable String label, HttpServletResponse response)
	{
		if(hashtagService.exists(label))
		{
			response.setStatus(HttpServletResponse.SC_FOUND);
			return tweetService.getTaggedTweets(label);
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
	

	@GetMapping("tweets/{id}/tags")
	public List<HashtagDto> getHashtagsInMessage(@PathVariable Integer id, HttpServletResponse response)
	{
		if(tweetService.exists(id))
		{
			response.setStatus(HttpServletResponse.SC_FOUND);
			return hashtagService.getTagsIn(id);
		}
		
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
}
