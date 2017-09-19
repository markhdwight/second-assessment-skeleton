package com.cooksys.Tweeter.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cooksys.Tweeter.entity.Tweet;

@Repository
public class TweetRepository {

	private EntityManager entityManager;
	
	public TweetRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public List<Tweet> getAllTweets()
	{
		return entityManager.createQuery("FROM Tweet",Tweet.class).getResultList();
	}
	
	public Tweet get(Integer id)
	{
		return entityManager.find(Tweet.class, id);	
	}
	
	@Transactional
	public Tweet create(Tweet tweet)
	{
		entityManager.persist(tweet);
		return tweet;
	}
}
