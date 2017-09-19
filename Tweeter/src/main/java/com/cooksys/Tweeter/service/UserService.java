package com.cooksys.Tweeter.service;

import com.cooksys.Tweeter.mapper.UserMapper;
import com.cooksys.Tweeter.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.Tweeter.dto.UserDto;
import com.cooksys.Tweeter.entity.User;

@Service
public class UserService 
{
	private UserRepository userRepo;
	private UserMapper userMapper;
	
	public UserService(UserRepository userRepo, UserMapper userMapper)
	{
		this.userRepo = userRepo;
		this.userMapper = userMapper;
	}
	
	public List<UserDto> getAll()
	{
		List<UserDto> users = new ArrayList<UserDto>();
		for(User u: userRepo.getAllUsers())
		{
			users.add(userMapper.toDto(u));
		}
		
		return users;
	}
	
	public UserDto get(Integer id)
	{
		return userMapper.toDto(userRepo.get(id));
	}
	
	public UserDto get(String username)
	{
		for(User u : userRepo.getAllUsers())
		{
			if(u.getUsername().equals(username))
			{
				if(u.isActive())
					return userMapper.toDto(u);
				else return null;
			}
		}
		return null;
	}
	
	public UserDto create(UserDto user)
	{
		return userMapper.toDto(userRepo.create(userMapper.fromDto(user)));
	}

	public boolean isActiveUser(UserDto u) {

		return userMapper.fromDto(u).isActive();
	}
}
