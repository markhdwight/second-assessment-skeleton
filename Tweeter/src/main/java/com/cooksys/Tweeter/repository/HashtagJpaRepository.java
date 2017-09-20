package com.cooksys.Tweeter.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cooksys.Tweeter.entity.Hashtag;

public interface HashtagJpaRepository extends JpaRepository<Hashtag, Integer> {

	List<Hashtag> findByLabel(String label); 
}
