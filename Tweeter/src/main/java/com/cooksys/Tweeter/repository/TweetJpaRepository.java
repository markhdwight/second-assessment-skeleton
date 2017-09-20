package com.cooksys.Tweeter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.Tweeter.entity.Tweet;

public interface TweetJpaRepository extends JpaRepository<Tweet, Integer> {
	
	List<Tweet> findByContentContainingAndActiveTrueOrderByTimestampDesc(String content);

	List<Tweet> findByInReplyToAndActiveTrue(Tweet tweet);
	
	List<Tweet> findByRepostOfAndActiveTrue(Tweet tweet);

	List<Tweet> findByAuthorIsAndActiveTrueOrderByTimestampDesc(String username);
}
