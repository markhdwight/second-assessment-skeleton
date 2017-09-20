package com.cooksys.Tweeter.dto;

import java.sql.Timestamp;

import com.cooksys.Tweeter.entity.Credentials;
import com.cooksys.Tweeter.entity.Profile;

public class TweeterUserDto {
	
	private Integer userId;
	
	private Credentials credentials;
	
	private Profile profile;
	
	private Timestamp joined;
	
	private boolean active;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
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
		credentials.setPassword(password);
	}
	
	public Profile getProfile()
	{
		return profile;
	}
	
	public void setProfile(Profile profile)
	{
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
