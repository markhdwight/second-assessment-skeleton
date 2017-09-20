package com.cooksys.Tweeter.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.Tweeter.entity.TweeterUser;

public interface TweeterUserJpaRepository extends JpaRepository<TweeterUser, Integer> 
{
	//List<TweeterUser> findBy
}
