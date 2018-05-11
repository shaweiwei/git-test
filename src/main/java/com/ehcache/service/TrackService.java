package com.ehcache.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ehcache.dao.TrackDao;
import com.ehcache.entity.Track;


@Service
public class TrackService {
	
	@Autowired
	private TrackDao trackDao;

	public void del(String uid) {
		// TODO Auto-generated method stub
		trackDao.del(uid);
	}

	public void update(Track track) {
		trackDao.update(track);
	}
	
	public Track getTrackById(String uid){
		System.err.println("缓存里没有"+uid+",所以这边没有走缓存，从数据库拿数据");
		return trackDao.findById(uid);
		
	}

	public boolean save(Track track) {
		// TODO Auto-generated method stub
		return trackDao.save(track);
	}

	
}
