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
import com.cooksys.Tweeter.service.TweeterUserService;

@RestController
@RequestMapping("user")
public class TweeterUserController 
{
	private TweeterUserService userService;
	
	public TweeterUserController(TweeterUserService userService)
	{
		this.userService = userService;
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
		return null;
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
	public TweeterUserDto updateProfile(@RequestBody Credentials credentials, @RequestBody Profile profile, @PathVariable String username)
	{
		return null;
	}
	
	@DeleteMapping("users/@{username}")
	public TweeterUserDto deactiveateUser(@RequestBody Credentials credentials, @PathVariable String username)
	{
		return null;
	}
	
	@PostMapping("users/@{username}/follow")
	public void follow(@RequestBody Credentials credentials, @PathVariable String username)
	{
		
	}
	
	@PostMapping("users/@{username}/unfollow")
	public void unfollow(@RequestBody Credentials credentials, @PathVariable String username)
	{
		
	}
	
	@GetMapping("users/@{username}/followers")
	public List<TweeterUserDto> getFollowers(@PathVariable String username)
	{
		return null;
	}
	
	@GetMapping("tweets/{id}/likes")
	public List<TweeterUserDto> getWhoLikes(@PathVariable Integer id)
	{
		return null;
	}
	
	@GetMapping("tweets/{id}/mentions")
	public List<TweeterUserDto> getThoseMentionedIn(@PathVariable Integer id)
	{
		return null;
	}
}
