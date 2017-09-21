package com.cooksys.Tweeter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.Tweeter.entity.TweeterUser;

public interface TweeterUserJpaRepository extends JpaRepository<TweeterUser, Integer> 
{
	List<TweeterUser> findByUserName(String username);
}
