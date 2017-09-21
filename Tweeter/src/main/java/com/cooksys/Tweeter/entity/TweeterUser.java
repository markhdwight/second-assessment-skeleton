package com.cooksys.Tweeter.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
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
	
//	@Embedded
//	private Credentials credentials;
//	
//	@Embedded
//	private Profile profile;
	
	@Column(unique = true,nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = true)
	private String firstName;
	
	@Column(nullable = true)
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = true)
	private String phoneNum;
	
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
		//credentials.setUsername(username);
		//this.profile = profile;
		this.userName = username;
		this.firstName = profile.getFirstName();
		this.lastName = profile.getLastName();
		this.email = profile.getEmail();
		this.phoneNum = profile.getPhoneNum();
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
	
	//@Column(name="username",unique = true,nullable = false)
	public String getUsername() {
		//return credentials.getUsername();
		return userName;
	}
	public void setUsername(String username) {
		//credentials.setUsername(username); 
		this.userName = username;
	}
	
	//@Column(name="password",nullable = false)
	public String getPassword() {
		//return credentials.getPassword();
		return password;
	}
	public void setPassword(String password) {
		//credentials.setPassword(password);;
		this.password = password;
	}
	public Profile getProfile() {
		//return profile;
		return new Profile(firstName,lastName,email,phoneNum);
	}
	public void setProfile(Profile profile) {
		this.firstName = profile.getFirstName();
		this.lastName = profile.getLastName();
		this.email = profile.getEmail();
		this.phoneNum = profile.getPhoneNum();
	}
	
	//@Column(name="email")
	public String getEmail()
	{
		//return profile.getEmail();
		return email;
	}
	
	public void setEmail(String email)
	{
		//profile.setEmail(email);
		this.email = email;
	}
	//@Column(name="firstname")
	public String getFirstName()
	{
		//return profile.getFirstName();
		return firstName;
	}
	
	public void setFirstName(String firstName)
	{
		//profile.setFirstName(firstName);
		this.firstName = firstName;
	}
	
	//@Column(name="lastname")
	public String getLastName()
	{
		//return profile.getLastName();
		return lastName;
	}
	
	public void setLastName(String lastName)
	{
		//profile.setLastName(lastName);
		this.lastName = lastName;
	}
	
	//@Column(name="phonenum")
	public String getPhoneNum()
	{
		//return profile.getPhoneNum();
		return phoneNum;
	}
	
	public void setPhoneNum(String phoneNum)
	{
		//profile.setPhoneNum(phoneNum);
		this.phoneNum = phoneNum;
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
		//follows.remove(dude);
		for(TweeterUser t : follows)
		{
			if(dude.getUsername().equals(t.getUsername()))
			{
				follows.remove(t);
				break;
			}
		}
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
		//followers.remove(dude);
		for(TweeterUser t : followers)
		{
			if(dude.getUsername().equals(t.getUsername()))
			{
				followers.remove(t);
				break;
			}
		}
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




