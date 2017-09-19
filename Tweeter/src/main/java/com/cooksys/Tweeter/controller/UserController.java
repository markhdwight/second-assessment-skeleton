package com.cooksys.Tweeter.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.Tweeter.dto.UserDto;
import com.cooksys.Tweeter.entity.Credentials;
import com.cooksys.Tweeter.entity.Profile;
import com.cooksys.Tweeter.service.UserService;

@RestController
@RequestMapping("user")
public class UserController 
{
	private UserService userService;
	
	public UserController(UserService userService)
	{
		this.userService = userService;
	}
	
	@GetMapping("validate/username/exists/@{username}")
	public boolean userExists(@PathVariable String username)
	{
		return false;
	}
	
	@GetMapping("validate/username/available/@{username}")
	public boolean usernameAvailable(@PathVariable String username)
	{
		return false;
	}
	
	@GetMapping
	public List<UserDto> getActiveUsers()
	{
		List<UserDto> users = new ArrayList<UserDto>();
		
		for(UserDto u : userService.getAll())
		{
			if(userService.isActiveUser(u))
				users.add(u);
		}
		
		return users;
	}
	
	@PostMapping
	public UserDto postUser(@RequestBody Credentials credentials,@RequestBody UserDto user)
	{
		return null;
	}
	
	@GetMapping("users/@{username}")
	public UserDto getUser(@PathVariable String username)
	{
		return userService.get(username);
		//TODO: handle null return
	}
	
	@PatchMapping("users/@{username}")
	public UserDto changeUsername(@RequestBody Credentials credentials, @RequestBody Profile profile, @PathVariable String username)
	{
		return null;
	}
	
	@DeleteMapping("users/@{username}")
	public UserDto deactiveateUser(@PathVariable String username)
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
	public List<UserDto> getFollowers(@PathVariable String username)
	{
		return null;
	}
	
	@GetMapping("tweets/{id}/likes")
	public List<UserDto> getWhoLikes(@PathVariable Integer id)
	{
		return null;
	}
	
	@GetMapping("tweets/{id}/mentions")
	public List<UserDto> getThoseMentionedIn(@PathVariable Integer id)
	{
		return null;
	}
}
