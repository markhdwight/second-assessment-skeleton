package com.cooksys.Tweeter.service;

import com.cooksys.Tweeter.mapper.TweeterUserMapper;
import com.cooksys.Tweeter.repository.TweetRepository;
import com.cooksys.Tweeter.repository.TweeterUserRepository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.cooksys.Tweeter.dto.TweeterUserDto;
import com.cooksys.Tweeter.entity.Credentials;
import com.cooksys.Tweeter.entity.Profile;
import com.cooksys.Tweeter.entity.Tweet;
import com.cooksys.Tweeter.entity.TweeterUser;

@Service
public class TweeterUserService 
{
	private TweeterUserRepository userRepo;
	private TweetRepository tweetRepo;
	private TweeterUserMapper userMapper;
	
	private EntityManager mgmt;
	
	public TweeterUserService(EntityManager mgmt, TweeterUserRepository userRepo, TweeterUserMapper userMapper, TweetRepository tweetRepo)
	{
		this.userRepo = userRepo;
		this.userMapper = userMapper;
		this.tweetRepo = tweetRepo;
		this.mgmt = mgmt;
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
	
	private TweeterUser getEntity(String username)
	{
		for(TweeterUser u : userRepo.getAllUsers())
		{
			if(u.getUsername().equals(username))
			{
				if(u.isActive())
					return u;
				else return null;
			}
		}
		return null;
	}


	public boolean isActiveUser(TweeterUserDto u) {

		return userMapper.fromDto(u).isActive();
	}

	public boolean exists(String username) {
		
		for(TweeterUser u: userRepo.getAllUsers())
		{
			if(u.getUsername().equals(username) && u.isActive());
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
			if(u.getUsername().equals(username) && u.getPassword().equals(password) && u.isActive())
				return u.getUserId();
		}
		return -1;
	}
	
	public Integer verifyDeactivatedUser(String username,String password)
	{
		for(TweeterUser u: userRepo.getAllUsers())
		{
			if(u.getUsername().equals(username) && u.getPassword().equals(password))
				return u.getUserId();
		}
		return -1;
	}

	public TweeterUserDto create(Credentials credentials, Profile profile) {

		TweeterUser user = new TweeterUser(credentials.getUsername(),profile);
		user.setPassword(credentials.getPassword());
		return userMapper.toDto(userRepo.create(user));
	}

	public TweeterUserDto activate(int id) 
	{
		TweeterUser user = userRepo.get(id);
		user.setActive(true);
		userRepo.update(user);
		return userMapper.toDto(user);
	}
	
	public TweeterUserDto deactivate(int id)
	{
		TweeterUser user = userRepo.get(id);
		user.setActive(false);
		userRepo.update(user);
		return userMapper.toDto(user);
	}

	public TweeterUserDto update(int id, Profile profile) {

		TweeterUser updated = userRepo.get(id);
		updated.setProfile(profile);
		userRepo.update(updated);
		return userMapper.toDto(updated);
	}

	@Transactional
	public void makeAFollowB(String usernameA, String usernameB) 
	{
		TweeterUser a = (TweeterUser) mgmt.createQuery("FROM TweeterUser WHERE userName = '" + usernameA + "'").getSingleResult();
		TweeterUser b = (TweeterUser) mgmt.createQuery("FROM TweeterUser WHERE userName = '" + usernameB + "'").getSingleResult();
		
		a.follow(b);
		b.addFollower(a);

//		userRepo.update(a);
//		userRepo.update(b);
		
	}
	
	public void makeAUnfollowB(String usernameA, String usernameB)
	{
//		TweeterUser a = mgmt.merge(userMapper.fromDto(get(usernameA)));
//		TweeterUser b = mgmt.merge(userMapper.fromDto(get(usernameB)));
		TweeterUser a = (TweeterUser) mgmt.createQuery("FROM TweeterUser WHERE userName = '" + usernameA + "'").getSingleResult();
		TweeterUser b = (TweeterUser) mgmt.createQuery("FROM TweeterUser WHERE userName = '" + usernameB + "'").getSingleResult();
		
		a.unfollow(b);
		b.removeFollower(a);
		
		userRepo.update(a);
		userRepo.update(b);
	}

	public List<TweeterUserDto> getFollowers(String username) {

		if(!exists(username))
			return null;
		
		TweeterUser user = getEntity(username);
		
		List<TweeterUserDto> followers = new ArrayList<TweeterUserDto>();
		
		for(TweeterUser f : user.getFollowers())
		{
			if(f.isActive())
				followers.add(userMapper.toDto(f));
		}
		
		return followers;
	}
	
	public List<TweeterUserDto> getFollowing(String username) {
		
		if(!exists(username))
			return null;
		
		TweeterUser user = getEntity(username);
		
		List<TweeterUserDto> following = new ArrayList<TweeterUserDto>();
		
		for(TweeterUser f : user.getFollows())
		{
			if(f.isActive())
				following.add(userMapper.toDto(f));
		}
		
		return following;
	}
	
	public List<TweeterUserDto> getWhoLikes(Integer id) {

		Tweet tweet = tweetRepo.get(id);
		List<TweeterUser> users = new ArrayList<TweeterUser>();
		
		for(TweeterUser u : tweet.getLikes())
		{
			if(u.isActive())
				users.add(u);
		}
		return userMapper.toDtos(users);
	}

	public List<TweeterUserDto> getThoseMentionedIn(Integer tweetId) {
		
		Tweet tweet = tweetRepo.get(tweetId);
		List<TweeterUser> users = new ArrayList<TweeterUser>();
		
		for(String s : tweet.getContent().split(" "))
		{
			if(s.charAt(0) == '@')
			{
				for(TweeterUser u : userRepo.getAllUsers())
				{
					if(u.getUsername().equals(s.substring(1)))
					{
						if(u.isActive())
							users.add(u);
					}
				}
			}
		}
		
		return userMapper.toDtos(users);
	}
	
}
