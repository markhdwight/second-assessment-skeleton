package com.cooksys.Tweeter.service;

import java.util.ArrayList;
import java.util.Collections;
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
	
	public List<TweetDto> getAllChronological()
	{
		List<Tweet> tweets = new ArrayList<Tweet>();
		List<TweetDto> tweetsDto = new ArrayList<TweetDto>();
		
		for(Tweet t: tweetRepo.getAllTweets())
		{
			tweets.add(t);
		}
		
		Collections.sort(tweets);
		
		for(Tweet t: tweets)
		{
			tweetsDto.add(tweetMapper.toDto(t));
		}
		
		return tweetsDto;
	}
	
	public TweetDto get(Integer id)
	{
		return tweetMapper.toDto(tweetRepo.get(id));
	}
	
	public TweetDto create(TweetDto tweet)
	{
		return tweetMapper.toDto(tweetRepo.create(tweetMapper.fromDto(tweet)));
	}

	public boolean areParentChild(TweetDto parentDto, TweetDto childDto) {
		
		Tweet parent = tweetMapper.fromDto(parentDto);
		Tweet child = tweetMapper.fromDto(childDto);
		if(child.getInReplyTo().equals(parent))
			return true;
		return false;
	}

	public List<TweetDto> getTaggedTweets(String label) {
		
		List<TweetDto> tweets = new ArrayList<TweetDto>();
		
		for(Tweet t: tweetRepo.getAllTweets())
		{
			if(t.getContent().contains("@"))
			{
				String[] contents = t.getContent().split("@");
				
				for(String s : contents)
				{
					if(s.split(" ")[0].equals(label))
					{
						tweets.add(tweetMapper.toDto(t));
						break;
					}
				}
			}
		}
		
		return tweets;
	}

}
