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
import com.cooksys.Tweeter.entity.TweetContext;
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
		List<Tweet> tweets = new ArrayList<Tweet>();
		for(Tweet t: tweetRepo.getAllTweets())
		{
			if(t.isActive())
				tweets.add(t);
		}
		
		Collections.sort(tweets);
		Collections.reverse(tweets);
		return tweetMapper.toDtos(tweets);
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
			if(t.isActive())
				tweetsDto.add(tweetMapper.toDto(t));
		}
		
		return tweetsDto;
	}
	
	public List<TweetDto> getTweetsBy(String username) {

		//return tweetMapper.toDtos(tweetJpaRepo.findByAuthorIsAndActiveTrueOrderByTimestampDesc(username));
	
		TweeterUser user = getEntity(username);
		List<Tweet> tweets = new ArrayList<Tweet>();
		
		for(Tweet t : tweetRepo.getAllTweets())
		{
			if(t.getAuthor().equals(user.getUsername()))
				tweets.add(t);
		}
		
		Collections.sort(tweets);
		Collections.reverse(tweets);
		
		return tweetMapper.toDtos(tweets);
		
	}
	
	public List<TweetDto> getFeedFor(String username) {

		//List<Tweet> tweets = tweetJpaRepo.findByAuthorIsAndActiveTrueOrderByTimestampDesc(username);
		TweeterUser user = getEntity(username);
		List<Tweet> allTweets = tweetRepo.getAllTweets();
		List<Tweet> feed = new ArrayList<Tweet>();
		
		for(TweeterUser u : user.getFollows())	//userJpaRepo.findByUserName(username).get(0).getFollows()
		{
			for(Tweet t : allTweets)
			{
				if(t.getAuthor().equals(u.getUsername()))
					feed.add(t);	//addAll(tweetJpaRepo.findByAuthorIsAndActiveTrueOrderByTimestampDesc(u.getUsername()))
				else if(t.getAuthor().equals(user.getUsername()))
					feed.add(t);
			}
			
		}
		
		Collections.sort(feed);
		Collections.reverse(feed);
		
		return tweetMapper.toDtos(feed);
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

	public boolean areParentChild(Tweet parent, Tweet child) {
		
		if(child.getInReplyTo().equals(parent))
			return true;
		return false;
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
		
		if(tweet == null)
		{
			return null;
		}
		
		Tweet reply = new Tweet(username,content);
		reply.setInReplyTo(tweet);
		
		tweetRepo.create(reply);
		
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
		
		if(tweet == null)
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
		
		if(tweet == null || !tweet.isActive())
			return null;
		
		List<Tweet> replies = new ArrayList<Tweet>();
		
		for(Tweet t : tweetRepo.getAllTweets())
		{
			if(t.getInReplyTo() != null)
			{
				if(t.getInReplyTo().getTweetId() == tweet.getTweetId())
					replies.add(t);
			}
		}
		
		return tweetMapper.toDtos(replies);
		//return tweetMapper.toDtos(tweetJpaRepo.findByInReplyToAndActiveTrue(tweet));
	}
	
	public List<TweetDto> getRepostsOf(Integer id)
	{
		Tweet tweet = tweetRepo.get(id);
		
		if(tweet == null || !tweet.isActive())
			return null;
		
		List<Tweet> reposts = new ArrayList<Tweet>();
		
		for(Tweet t : tweetRepo.getAllTweets())
		{
			if(t.getRepostOf() != null)
			{
				if(t.getRepostOf().getTweetId() == tweet.getTweetId())
					reposts.add(t);
			}
		}
		
		return tweetMapper.toDtos(reposts);
		//return tweetMapper.toDtos(tweetJpaRepo.findByRepostOfAndActiveTrue(tweet));
	}

	public void addToLikes(Integer tweetId, Integer userId) {

		Tweet tweet = tweetRepo.get(tweetId);
		TweeterUser dude = userRepo.get(userId);
		
		tweet.likedBy(dude);
		tweetRepo.update(tweet);
	}
	
	private TweeterUser getEntity(String username)
	{
		for(TweeterUser u : userRepo.getAllUsers())
		{
			if(u.getUsername().equals(username))
			{
				if(u.isActive())
					return u;
				else return null;
			}
		}
		return null;
	}

	public TweetContext getContext(Integer id) {	//TODO MAYBE

		if(!exists(id))
			return null;
		
		List<Tweet> allTweets = tweetRepo.getAllTweets();
		Collections.sort(allTweets);
		List<Tweet> before = new ArrayList<Tweet>();
		List<Tweet> after = new ArrayList<Tweet>();
		Tweet origin = tweetRepo.get(id);
		
		before = getParentTweets(origin,allTweets.subList(0,allTweets.indexOf(origin)));
		after = getChildTweets(origin,allTweets.subList(allTweets.indexOf(origin),allTweets.size()));
		
		TweetContext context = new TweetContext(tweetMapper.toDto(origin));
		context.setBefore(tweetMapper.toDtos(before));
		context.setAfter(tweetMapper.toDtos(after));
		
		return context;
	}
	
	private List<Tweet> getParentTweets(Tweet start,List<Tweet> listSegment)
	{
		List<Tweet> list = new ArrayList<Tweet>();
		
		if(listSegment.size() == 0)
			return list;
		
		Tweet newStart = listSegment.get(0);
		
		for(int i = listSegment.size()-1; i>=0; i--)
		{
			if(areParentChild(listSegment.get(i),start))
			{
				newStart = listSegment.get(i);
				list.add(newStart);
				break;	
			}
		}
		
		List<Tweet> head = getParentTweets(newStart,listSegment.subList(0, listSegment.indexOf(newStart)-1));
		
		head.addAll(list);
		
		return head;
	}
	
	private List<Tweet> getChildTweets(Tweet start,List<Tweet> listSegment)
	{	
		List<Tweet> list = new ArrayList<Tweet>();
		
		if(listSegment.size() == 0)
			return list;
		
		Tweet newStart = listSegment.get(listSegment.size()-1);
		
		for(Tweet t: listSegment)
		{
			if(areParentChild(start,t));
			{
				newStart = t;
				list.add(newStart);
				break;
			}
		}
		
		list.addAll(getChildTweets(newStart,listSegment.subList(listSegment.indexOf(newStart)+1, listSegment.size())));
		
		return list;
	}

}
