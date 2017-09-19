package com.cooksys.Tweeter.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.Tweeter.mapper.TweetMapper;
import com.cooksys.Tweeter.repository.TweetRepository;
import com.cooksys.Tweeter.dto.TweetDto;
import com.cooksys.Tweeter.entity.Tweet;

@Service
public class TweetService {
	
	private TweetRepository tweetRepo;
	private TweetMapper tweetMapper;
	
	public TweetService(TweetRepository tweetRepo,TweetMapper tweetMapper)
	{
		this.tweetRepo = tweetRepo;
		this.tweetMapper = tweetMapper;
	}
	
	public List<TweetDto> getAll()
	{
		List<TweetDto> tweets = new ArrayList<TweetDto>();
		for(Tweet t: tweetRepo.getAllTweets())
		{
			tweets.add(tweetMapper.toDto(t));
		}
		
		return tweets;
	}
	
	public TweetDto get(Integer id)
	{
		return tweetMapper.toDto(tweetRepo.get(id));
	}
	
	public TweetDto create(TweetDto tweet)
	{
		return tweetMapper.toDto(tweetRepo.create(tweetMapper.fromDto(tweet)));
	}

}
