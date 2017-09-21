package com.cooksys.Tweeter.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TweeterUser {

	@Id
	@GeneratedValue
	private Integer userId;
	
	@Embedded
	private Credentials credentials;
	
	@Embedded
	private Profile profile;
	
	private Timestamp joined;
	
	private boolean active;
	
	@OneToMany
	private List<TweeterUser> follows;
	
	@OneToMany
	private List<TweeterUser> followers;
	
	public TweeterUser()
	{
		
	}
	
	public TweeterUser(String username,Profile profile)
	{
		credentials.setUsername(username);
		this.profile = profile;
		this.joined = new Timestamp(System.currentTimeMillis());
		this.active = true;
		follows = new ArrayList<TweeterUser>();
		followers = new ArrayList<TweeterUser>();
	}
	
	public Integer getUserId()
	{
		return userId;
	}
	
	public void setUserId(Integer userId)
	{
		this.userId = userId;
	}
	
	public String getUsername() {
		return credentials.getUsername();
	}
	public void setUsername(String username) {
		credentials.setUsername(username); 
	}
	public String getPassword() {
		return credentials.getPassword();
	}
	public void setPassword(String password) {
		credentials.setPassword(password);;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public String getEmail()
	{
		return profile.getEmail();
	}
	
	public void setEmail(String email)
	{
		profile.setEmail(email);
	}
	
	public String getFirstName()
	{
		return profile.getFirstName();
	}
	
	public void setFirstName(String firstName)
	{
		profile.setFirstName(firstName);
	}
	
	public String getLastName()
	{
		return profile.getLastName();
	}
	
	public void setLastName(String lastName)
	{
		profile.setLastName(lastName);
	}
	
	public String getPhoneNum()
	{
		return profile.getPhoneNum();
	}
	
	public void setPhoneNum(String phoneNum)
	{
		profile.setPhoneNum(phoneNum);
	}
	public Timestamp getJoined() {
		return joined;
	}
	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}
	public boolean isActive()
	{
		return active;
	}
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	public List<TweeterUser> getFollows()
	{
		return follows;
	}
	
	public void setFollows(List<TweeterUser> follows)
	{
		this.follows = follows;
	}
	
	public void follow(TweeterUser dude)
	{
		follows.add(dude);
	}
	
	public void unfollow(TweeterUser dude)
	{
		follows.remove(dude);
	}
	
	public List<TweeterUser> getFollowers()
	{
		return followers;
	}
	
	public void setFollowers(List<TweeterUser> followers)
	{
		this.followers = followers;
	}
	
	public void addFollower(TweeterUser dude)
	{
		followers.add(dude);
	}
	
	public void removeFollower(TweeterUser dude)
	{
		followers.remove(dude);
	}
	
	public boolean isFollowing(TweeterUser dude)
	{
		return dude.followers.contains(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TweeterUser other = (TweeterUser) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
}




