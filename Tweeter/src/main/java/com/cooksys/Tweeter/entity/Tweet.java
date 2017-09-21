package com.cooksys.Tweeter.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Tweet implements Comparable<Tweet>{

	@Id
	@GeneratedValue
	private Integer tweetId;
	
	private String author;
	
	@Column(nullable = true)
	private String content;
	
	private Timestamp timestamp;
	
	@OneToOne
	private Tweet inReplyTo;
	
	@OneToOne
	private Tweet repostOf;
	
	private boolean active;
	
	@OneToMany
	private List<TweeterUser> likes;// = new ArrayList<TweeterUser>();
	
	public Tweet()
	{
		
	}
	
	public Tweet(String author,String content)
	{
		this.author = author;
		this.content = content;
		this.active = true;
		timestamp = new Timestamp(System.currentTimeMillis());
		likes = new ArrayList<TweeterUser>();
	}
	
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
	
	public List<TweeterUser> getLikes() {
		return likes;
	}

	public void setLikes(List<TweeterUser> likes) {
		this.likes = likes;
	}
	
	public void likedBy(TweeterUser dude)
	{
		likes.add(dude);
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
	public int compareTo(Tweet other) {

		return this.timestamp.compareTo(other.getTimestamp());
	}

	public List<Hashtag> getHashTags() {

		List<Hashtag> tags = new ArrayList<Hashtag>();
		
		for(String s : content.split(" "))
		{
			if(s.charAt(0) == '#')
			{	
				tags.add(new Hashtag(s.substring(1)));
			}
		}
		
		return tags;
	}
	
}
