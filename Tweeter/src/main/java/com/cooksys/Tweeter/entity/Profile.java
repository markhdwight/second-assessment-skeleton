package com.cooksys.Tweeter.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Profile
{
	String firstName;
	
	String lastName;
	
	@Column(nullable = false, unique = true)
	String email;
	
	String phoneNum;
	
	public Profile()
	{
		
	}

	public Profile(String firstName, String lastName, String email, String phoneNum) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNum = phoneNum;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
}