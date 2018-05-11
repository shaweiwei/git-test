package com.ehcache.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ehcache.dao.UserDao;
import com.ehcache.entity.User;


@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	@CacheEvict(key="'user_'+#uid", value="userCache")
	public void del(String uid) {
		// TODO Auto-generated method stub
		userDao.del(uid);
	}

	@CachePut(key="'user_'+#user.uid", value="userCache")
	public void update(User user) {
		userDao.update(user);
	}
	
	@Cacheable(key="'user_'+#uid",value="userCache")
	public User getUserById(String uid){
		System.err.println("缓存里没有"+uid+",所以这边没有走缓存，从数据库拿数据");
		return userDao.findById(uid);
		
	}

	@CacheEvict(key="'user'",value="userCache")
	public String save(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}

	
}
