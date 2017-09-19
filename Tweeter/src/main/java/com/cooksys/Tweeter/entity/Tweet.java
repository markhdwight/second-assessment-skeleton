package com.cooksys.Tweeter.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tweet implements Comparable{

	@Id
	@GeneratedValue
	private Integer tweetId;
	
	private String author;
	
	private String content;
	
	private Timestamp timestamp;
	
	private Tweet inReplyTo;
	
	private Tweet repostOf;
	
	private boolean active;
	
	public Integer getTweetId() {
		return tweetId;
	}
	public void setTweetId(Integer tweetId) {
		this.tweetId = tweetId;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public Tweet getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public Tweet getRepostOf() {
		return repostOf;
	}
	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tweetId == null) ? 0 : tweetId.hashCode());
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
		Tweet other = (Tweet) obj;
		if (tweetId == null) {
			if (other.tweetId != null)
				return false;
		} else if (!tweetId.equals(other.tweetId))
			return false;
		return true;
	}
	@Override
	public int compareTo(Object other) {

		return compareTweets((Tweet)other);
	}
	
	public int compareTweets(Tweet other)
	{
		return this.timestamp.compareTo(other.getTimestamp());
	}
	
}
