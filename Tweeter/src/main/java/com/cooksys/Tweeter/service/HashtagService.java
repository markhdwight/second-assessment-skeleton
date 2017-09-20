package com.cooksys.Tweeter.service;

import com.cooksys.Tweeter.entity.Hashtag;
import com.cooksys.Tweeter.mapper.HashtagMapper;
import com.cooksys.Tweeter.repository.HashtagJpaRepository;
import com.cooksys.Tweeter.repository.HashtagRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cooksys.Tweeter.dto.HashtagDto;

@Service
public class HashtagService {
	
	private HashtagRepository hashtagRepo;
	private HashtagJpaRepository hashtagJpaRepo;
	private HashtagMapper hashtagMapper;
	
	public HashtagService(HashtagRepository hashtagRepo, HashtagMapper hashtagMapper)
	{
		this.hashtagRepo = hashtagRepo;
		this.hashtagMapper = hashtagMapper;
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

}
