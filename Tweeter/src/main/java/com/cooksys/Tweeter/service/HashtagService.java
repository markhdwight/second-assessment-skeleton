package com.cooksys.Tweeter.service;

import com.cooksys.Tweeter.entity.Hashtag;
import com.cooksys.Tweeter.mapper.HashtagMapper;
import com.cooksys.Tweeter.repository.HashtagJpaRepository;
import com.cooksys.Tweeter.repository.HashtagRepository;
import com.cooksys.Tweeter.repository.TweetRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.Tweeter.dto.HashtagDto;

@Service
public class HashtagService {
	
	private HashtagRepository hashtagRepo;
	private HashtagJpaRepository hashtagJpaRepo;
	private TweetRepository tweetRepo;
	private HashtagMapper hashtagMapper;
	
	public HashtagService(HashtagRepository hashtagRepo, HashtagMapper hashtagMapper,TweetRepository tweetRepo)
	{
		this.hashtagRepo = hashtagRepo;
		this.hashtagMapper = hashtagMapper;
		this.tweetRepo = tweetRepo;
	}
	
	public List<HashtagDto> getAll()
	{
		List<HashtagDto> hashtags = new ArrayList<HashtagDto>();
		
		for(Hashtag h : hashtagRepo.getAllHashtags())
		{
			hashtags.add(hashtagMapper.toDto(h));
		}
		
		return hashtags;
	}
	
	public HashtagDto get(Integer id)
	{
		return hashtagMapper.toDto(hashtagRepo.get(id));
	}
	
	public HashtagDto create(HashtagDto hashtag)
	{
		hashtagRepo.create(hashtagMapper.fromDto(hashtag));
		return hashtag;
	}

	public boolean exists(String label) {

		return hashtagJpaRepo.findByLabel(label).size()>0;
	}

	public List<HashtagDto> getTagsIn(Integer id) {

		List<HashtagDto> tagsFinal = new ArrayList<HashtagDto>();
		List<Hashtag> tagsRaw = tweetRepo.get(id).getHashTags();
		
		for(Hashtag h : tagsRaw)
		{
			Hashtag hPrime = hashtagJpaRepo.findByLabel(h.getLabel()).get(0);
			tagsFinal.add(hashtagMapper.toDto(hPrime));
		}
		
		return tagsFinal;
	}
}
