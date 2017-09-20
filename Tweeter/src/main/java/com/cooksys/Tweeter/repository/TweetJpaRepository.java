package com.cooksys.Tweeter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.Tweeter.entity.Tweet;

public interface TweetJpaRepository extends JpaRepository<Tweet, Integer> {
	
	List<Tweet> findByContentContainingOrderByTimestampDesc(String content);

}
