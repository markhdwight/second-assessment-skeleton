package com.cooksys.Tweeter.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.Tweeter.mapper.TweetMapper;
import com.cooksys.Tweeter.repository.HashtagRepository;
import com.cooksys.Tweeter.repository.TweetJpaRepository;
import com.cooksys.Tweeter.repository.TweetRepository;
import com.cooksys.Tweeter.dto.TweetDto;
import com.cooksys.Tweeter.entity.Hashtag;
import com.cooksys.Tweeter.entity.Tweet;

@Service
public class TweetService {
	
	private TweetRepository tweetRepo;
	private TweetJpaRepository tweetJpaRepo;
	private HashtagRepository hashtagRepo;
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
		
		return tweetMapper.toDtos(tweetJpaRepo.findByContentContainingOrderByTimestampDesc("#"+label));
	}

	public List<TweetDto> getMentions(String username) {
		
		return tweetMapper.toDtos(tweetJpaRepo.findByContentContainingOrderByTimestampDesc("@"+username));
		
	}

	public TweetDto create(String content, String username) {
		
		Tweet tweet = new Tweet(username,content);
		List<Hashtag> masterTagList = hashtagRepo.getAllHashtags();
		List<Hashtag> tagList = tweet.getHashTags();
		boolean tagExists;
		
		tweetRepo.create(tweet);
		
		for(Hashtag h : tagList)
		{
			tagExists = false;
			
			for(int i = 0; i<masterTagList.size(); i++)
			{
				if(masterTagList.get(i).getLabel().equals(h.getLabel()))
				{
					h.setLastUpdated(new Timestamp(System.currentTimeMillis()));
					hashtagRepo.update(h);
					tagExists = true;
				}
			}
			if(!tagExists)
				hashtagRepo.create(h);
		}
		
		return tweetMapper.toDto(tweet);
	}

}
