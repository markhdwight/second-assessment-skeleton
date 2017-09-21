package com.cooksys.Tweeter.controller;

import java.util.ArrayList;
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

import com.cooksys.Tweeter.dto.TweeterUserDto;
import com.cooksys.Tweeter.entity.Credentials;
import com.cooksys.Tweeter.entity.Profile;
import com.cooksys.Tweeter.service.TweetService;
import com.cooksys.Tweeter.service.TweeterUserService;

@RestController
@RequestMapping("user")
public class TweeterUserController 
{
	private TweeterUserService userService;
	private TweetService tweetService;
	
	public TweeterUserController(TweeterUserService userService,TweetService tweetService)
	{
		this.userService = userService;
		this.tweetService = tweetService;
	}
	
	@GetMapping("validate/username/exists/@{username}")
	public boolean userExists(@PathVariable String username)
	{
		return userService.exists(username);
	}
	
	@GetMapping("validate/username/available/@{username}")
	public boolean usernameAvailable(@PathVariable String username)
	{
		return userService.isAvailable(username);
	}
	
	@GetMapping("users")
	public List<TweeterUserDto> getActiveUsers()
	{
		List<TweeterUserDto> users = new ArrayList<TweeterUserDto>();
		
		for(TweeterUserDto u : userService.getAll())
		{
			if(userService.isActiveUser(u))
				users.add(u);
		}
		
		return users;
	}
	
	@PostMapping("users")
	public TweeterUserDto postUser(@RequestBody Credentials credentials,@RequestBody Profile profile,HttpServletResponse response)
	{
		if(userService.exists(credentials.getUsername()))	//Check to see if the user exists already, and either exit or reactivate the user
		{
			int id = userService.verifyUser(credentials.getUsername(),credentials.getPassword());
			
			if(id>0)
			{
				if(userService.isActiveUser(userService.get(id)))
				{
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					return null;
				}
				else return userService.activate(id);		
			}
		}
		if(profile.getEmail().equals(null) || !credentials.areComplete())	//Check to see that email and credentials are provided in the request
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		TweeterUserDto user = userService.create(credentials,profile);
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return user;
	}
	
	@GetMapping("users/@{username}")
	public TweeterUserDto getUser(@PathVariable String username,HttpServletResponse response)
	{
		TweeterUserDto user = userService.get(username);
		if(user.equals(null))
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		else response.setStatus(HttpServletResponse.SC_FOUND); 
			
		return user;
	}
	
	@PatchMapping("users/@{username}")
	public TweeterUserDto updateProfile(@RequestBody Credentials credentials, @RequestBody Profile profile, @PathVariable String username,HttpServletResponse response)
	{
		if(!userService.exists(credentials.getUsername()))
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		int id = userService.verifyUser(credentials.getUsername(),credentials.getPassword());
		if(id < 0)
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
		
		if(!userService.isActiveUser(userService.get(id)))
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
		
		response.setStatus(HttpServletResponse.SC_ACCEPTED);
		return userService.update(id,profile);
	}
	
	@DeleteMapping("users/@{username}")
	public TweeterUserDto deactiveateUser(@RequestBody Credentials credentials, @PathVariable String username,HttpServletResponse response)
	{
		int id = userService.verifyUser(credentials.getUsername(),credentials.getPassword());
		if(id > 0)
		{	
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return userService.deactivate(id);
		}
		response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		return null;
	}
	
	@PostMapping("users/@{username}/follow")
	public void follow(@RequestBody Credentials credentials, @PathVariable String username, HttpServletResponse response)
	{
		int id = userService.verifyUser(credentials.getUsername(),credentials.getPassword());
		
		if(id > 0 && userService.exists(username))
		{
			userService.makeAFollowB(credentials.getUsername(),username);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}
		else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	@PostMapping("users/@{username}/unfollow")
	public void unfollow(@RequestBody Credentials credentials, @PathVariable String username, HttpServletResponse response)
	{
		int id = userService.verifyUser(credentials.getUsername(),credentials.getPassword());
		
		if(id > 0 && userService.exists(username))
		{
			userService.makeAUnfollowB(credentials.getUsername(),username);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
		}
		else response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	}
	
	@GetMapping("users/@{username}/followers")
	public List<TweeterUserDto> getFollowers(@PathVariable String username, HttpServletResponse response)
	{
		List<TweeterUserDto> followers = userService.getFollowers(username);
		
		if(followers.equals(null))
		{
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		
		response.setStatus(HttpServletResponse.SC_FOUND);
		return followers;
	}
	
	@GetMapping("tweets/{id}/likes")
	public List<TweeterUserDto> getWhoLikes(@PathVariable Integer id, HttpServletResponse response)
	{
		if(tweetService.exists(id))
		{
			response.setStatus(HttpServletResponse.SC_FOUND);
			return userService.getWhoLikes(id);
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
	
	@GetMapping("tweets/{id}/mentions")
	public List<TweeterUserDto> getThoseMentionedIn(@PathVariable Integer id, HttpServletResponse response)
	{
		if(tweetService.exists(id))
		{
			response.setStatus(HttpServletResponse.SC_FOUND);
			return userService.getThoseMentionedIn(id);
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		return null;
	}
}
