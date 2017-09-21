package com.cooksys.Tweeter.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.Tweeter.mapper.TweetMapper;
import com.cooksys.Tweeter.repository.HashtagJpaRepository;
import com.cooksys.Tweeter.repository.HashtagRepository;
import com.cooksys.Tweeter.repository.TweetJpaRepository;
import com.cooksys.Tweeter.repository.TweetRepository;
import com.cooksys.Tweeter.repository.TweeterUserJpaRepository;
import com.cooksys.Tweeter.repository.TweeterUserRepository;
import com.cooksys.Tweeter.dto.HashtagDto;
import com.cooksys.Tweeter.dto.TweetDto;
import com.cooksys.Tweeter.dto.TweeterUserDto;
import com.cooksys.Tweeter.entity.Hashtag;
import com.cooksys.Tweeter.entity.Tweet;
import com.cooksys.Tweeter.entity.TweeterUser;

@Service
public class TweetService {
	
	private TweetRepository tweetRepo;
	private TweetJpaRepository tweetJpaRepo;
	private TweeterUserRepository userRepo;
	private TweeterUserJpaRepository userJpaRepo;
	private HashtagRepository hashtagRepo;
	private TweetMapper tweetMapper;
	
	public TweetService(TweetRepository tweetRepo,TweetMapper tweetMapper, TweeterUserRepository userRepo, TweeterUserJpaRepository userJpaRepo, HashtagRepository hashtagRepo)
	{
		this.tweetRepo = tweetRepo;
		this.tweetMapper = tweetMapper;
		this.userRepo = userRepo;
		this.userJpaRepo = userJpaRepo;
		this.hashtagRepo = hashtagRepo;
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
	
	public List<TweetDto> getTweetsBy(String username) {

		return tweetMapper.toDtos(tweetJpaRepo.findByAuthorIsAndActiveTrueOrderByTimestampDesc(username));
	}
	
	public List<TweetDto> getFeedFor(String username) {

		List<Tweet> tweets = tweetJpaRepo.findByAuthorIsAndActiveTrueOrderByTimestampDesc(username);
		
		for(TweeterUser u : userJpaRepo.findByUsername(username).get(0).getFollows())
		{
			tweets.addAll(tweetJpaRepo.findByAuthorIsAndActiveTrueOrderByTimestampDesc(u.getUsername()));
		}
		
		Collections.sort(tweets);
		Collections.reverse(tweets);
		
		return tweetMapper.toDtos(tweets);
	}
	
	public TweetDto get(Integer id)
	{
		return tweetMapper.toDto(tweetRepo.get(id));
	}
	

	public boolean exists(Integer id) {
		for(Tweet t: tweetRepo.getAllTweets())
		{
			if(t.getTweetId() == id && t.isActive())
				return true;
		}
		return false;
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
		
		return tweetMapper.toDtos(tweetJpaRepo.findByContentContainingAndActiveTrueOrderByTimestampDesc("#"+label));
	}

	public List<TweetDto> getMentions(String username) {
		
		return tweetMapper.toDtos(tweetJpaRepo.findByContentContainingAndActiveTrueOrderByTimestampDesc("@"+username));
		
	}

	public TweetDto create(String content, String username) {
		
		Tweet tweet = new Tweet(username,content);
		
		tweetRepo.create(tweet);
		
		updateHashtags(tweet);
		
		return tweetMapper.toDto(tweet);
	}
	
	public TweetDto createReply(String username, Integer id, String content) {

		Tweet tweet = tweetRepo.get(id);
		
		if(tweet.equals(null))
		{
			return null;
		}
		
		Tweet reply = new Tweet(username,content);
		reply.setInReplyTo(tweet);
		
		tweetRepo.create(tweet);
		
		updateHashtags(tweet);
		
		return tweetMapper.toDto(reply);
	}
	
	private void updateHashtags(Tweet tweet)
	{
		List<Hashtag> masterTagList = hashtagRepo.getAllHashtags();
		List<Hashtag> tagList = tweet.getHashTags();
		boolean tagExists;
		
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
	}
	
	public TweetDto createRepost(String username, Integer id) {

		Tweet tweet = tweetRepo.get(id);
		
		if(tweet.equals(null))
		{
			return null;
		}
		
		Tweet repost = new Tweet(username,"");
		repost.setRepostOf(tweet);
		
		tweetRepo.create(repost);
		
		return tweetMapper.toDto(repost);
	}
	
	public TweetDto activate(Integer id)
	{
		Tweet tweet = tweetRepo.get(id);
		tweet.setActive(true);
		tweetRepo.update(tweet);
		return tweetMapper.toDto(tweet);
	}

	public TweetDto deactivate(Integer id) {

		Tweet tweet = tweetRepo.get(id);
		tweet.setActive(false);
		tweetRepo.update(tweet);
		return tweetMapper.toDto(tweet);
	}

	public List<TweetDto> getRepliesOf(Integer id) {

		Tweet tweet = tweetRepo.get(id);
		
		if(tweet.equals(null) || !tweet.isActive())
			return null;
		
		return tweetMapper.toDtos(tweetJpaRepo.findByInReplyToAndActiveTrue(tweet));
	}
	
	public List<TweetDto> getRepostsOf(Integer id)
	{
		Tweet tweet = tweetRepo.get(id);
		
		if(tweet.equals(null) || !tweet.isActive())
			return null;
		
		return tweetMapper.toDtos(tweetJpaRepo.findByRepostOfAndActiveTrue(tweet));
	}

	public void addToLikes(Integer tweetId, Integer userId) {

		Tweet tweet = tweetRepo.get(tweetId);
		TweeterUser dude = userRepo.get(userId);
		
		tweet.likedBy(dude);
	}

}
