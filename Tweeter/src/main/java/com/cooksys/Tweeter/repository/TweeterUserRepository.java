package com.cooksys.Tweeter.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cooksys.Tweeter.entity.TweeterUser;

@Repository
public class TweeterUserRepository {
	
	private EntityManager entityManager;
	
	public TweeterUserRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public TweeterUser get(Integer id)
	{
		return entityManager.find(TweeterUser.class,id);
	}
	
	public List<TweeterUser> getAllUsers()
	{
		return entityManager.createQuery("FROM TweeterUser",TweeterUser.class).getResultList();
	}
	
	@Transactional
	public TweeterUser create(TweeterUser user)
	{
		entityManager.persist(user);
		return user;
	}
	
	@Transactional
	public TweeterUser update(TweeterUser user)
	{
		TweeterUser result = entityManager.merge(user);
		System.out.println(result.getFollowers());
		return user;
	}

}
