package com.cooksys.Tweeter.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Credentials
{
	//@Column(unique = true,nullable = false)
	private String userName;
	
	//@Column(nullable = false)
	private String password;

	public String getUsername() {
		return userName;
	}

	public void setUsername(String username) {
		this.userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean areComplete() {
		// TODO Auto-generated method stub
		return (!userName.equals(null) & !password.equals(null));
	}	
}