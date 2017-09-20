package com.cooksys.Tweeter.service;

import com.cooksys.Tweeter.mapper.TweeterUserMapper;
import com.cooksys.Tweeter.repository.TweeterUserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.Tweeter.dto.TweeterUserDto;
import com.cooksys.Tweeter.entity.Credentials;
import com.cooksys.Tweeter.entity.Profile;
import com.cooksys.Tweeter.entity.TweeterUser;

@Service
public class TweeterUserService 
{
	private TweeterUserRepository userRepo;
	private TweeterUserMapper userMapper;
	
	public TweeterUserService(TweeterUserRepository userRepo, TweeterUserMapper userMapper)
	{
		this.userRepo = userRepo;
		this.userMapper = userMapper;
	}
	
	public List<TweeterUserDto> getAll()
	{
		List<TweeterUserDto> users = new ArrayList<TweeterUserDto>();
		for(TweeterUser u: userRepo.getAllUsers())
		{
			users.add(userMapper.toDto(u));
		}
		
		return users;
	}
	
	public TweeterUserDto get(Integer id)
	{
		return userMapper.toDto(userRepo.get(id));
	}
	
	public TweeterUserDto get(String username)
	{
		for(TweeterUser u : userRepo.getAllUsers())
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
	
	public TweeterUserDto create(TweeterUserDto user)
	{
		return userMapper.toDto(userRepo.create(userMapper.fromDto(user)));
	}

	public boolean isActiveUser(TweeterUserDto u) {

		return userMapper.fromDto(u).isActive();
	}

	public boolean exists(String username) {
		
		for(TweeterUser u: userRepo.getAllUsers())
		{
			if(u.getUsername().equals(username));
				return true;
		}
		return false;
	}

	public boolean isAvailable(String username) {
		for(TweeterUser u: userRepo.getAllUsers())
		{
			if(u.getUsername().equals(username));
				return false;
		}
		return true;
	}
	
	public Integer verifyUser(String username,String password)
	{	
		for(TweeterUser u: userRepo.getAllUsers())
		{
			if(u.getUsername().equals(username) && u.getPassword().equals(password));
				return u.getUserId();
		}
		return -1;
	}

	public TweeterUserDto create(Credentials credentials, Profile profile) {

		TweeterUser user = new TweeterUser(credentials.getUsername(),profile);
		user.setPassword(credentials.getPassword());
		return null;
	}

	public TweeterUserDto activate(int id) 
	{
		userMapper.toDto(userRepo.get(id)).setActive(true);
		return userMapper.toDto(userRepo.get(id));
	}
	
	public TweeterUserDto deactivate(int id)
	{
		userMapper.toDto(userRepo.get(id)).setActive(false);
		return userMapper.toDto(userRepo.get(id));
	}

	public TweeterUserDto update(int id, Profile profile) {

		TweeterUser updated = userRepo.get(id);
		updated.setProfile(profile);
		userRepo.update(updated);
		return userMapper.toDto(updated);
	}
}
