package com.cooksys.Tweeter.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Hashtag {

	@Id
	@GeneratedValue
	private Integer hashtagID;
	
	private String label;
	
	private Timestamp firstUsed;
	
	private Timestamp lastUpdated;
	
	public Hashtag()
	{
		
	}
	
	public Hashtag(String label)
	{
		this.label = label;
		firstUsed = new Timestamp(System.currentTimeMillis());
		lastUpdated = firstUsed;
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hashtagID == null) ? 0 : hashtagID.hashCode());
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
		Hashtag other = (Hashtag) obj;
		if (hashtagID == null) {
			if (other.hashtagID != null)
				return false;
		} else if (!hashtagID.equals(other.hashtagID))
			return false;
		return true;
	}
	
}
