package com.cooksys.Tweeter.dto;

import java.sql.Timestamp;

public class HashtagDto 
{
	private String label;
	private Timestamp firstUsed;
	private Timestamp lastUpdated;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Timestamp getFirstUsed() {
		return firstUsed;
	}
	public void setFirstUsed(Timestamp firstUsed) {
		this.firstUsed = firstUsed;
	}
	public Timestamp getLastUpdated() {
		return lastUpdated;
	}
	public void setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
