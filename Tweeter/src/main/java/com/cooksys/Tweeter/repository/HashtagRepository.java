package com.cooksys.Tweeter.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cooksys.Tweeter.entity.Hashtag;

@Repository
public class HashtagRepository {

	private EntityManager entityManager;
	
	public HashtagRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public Hashtag get(Integer id)
	{
		return entityManager.find(Hashtag.class, id);
	}
	
	@Transactional
	public Hashtag create(Hashtag hashtag)
	{
		entityManager.persist(hashtag);
		return hashtag;
	}

	public List<Hashtag> getAllHashtags() {

		return entityManager.createQuery("FROM Hashtag",Hashtag.class).getResultList();
	}
}
