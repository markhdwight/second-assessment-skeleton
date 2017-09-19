package com.cooksys.Tweeter.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cooksys.Tweeter.entity.User;

@Repository
public class UserRepository {
	
	private EntityManager entityManager;
	
	public UserRepository(EntityManager entityManager)
	{
		this.entityManager = entityManager;
	}
	
	public User get(Integer id)
	{
		return entityManager.find(User.class,id);
	}
	
	public List<User> getAllUsers()
	{
		return entityManager.createQuery("FIND User",User.class).getResultList();
	}
	
	@Transactional
	public User create(User user)
	{
		entityManager.persist(user);
		return user;
	}

}
