package com.ehcache.factory;

import java.awt.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import com.ehcache.entity.User;


public class UserFactory {

	public static HashMap<String, User> umap = new HashMap<>();
	
//	static{
//		User user = new User();
//		String uid = UUID.randomUUID().toString();
//		user.setId(uid);
//		user.setUsername("小王"+new Date());
//		user.setPassword(""+new Random().nextInt(10)+new Random().nextInt(10)+new Random().nextInt(10)+new Random().nextInt(10));
//		
//		umap.put(uid, user);
//	}
	
	public static User createUser(){
		User user = new User();
		String uid = UUID.randomUUID().toString();
		user.setUid(uid);
		user.setUsername("小王");
		user.setPassword(""+new Random().nextInt(10)+new Random().nextInt(10)+new Random().nextInt(10)+new Random().nextInt(10));
		return user;
	}
	
	public static void addUser(User user){
		umap.put(user.getUid(), user);
	}
	
	public static void delUser(String uid){
		umap.remove(uid);
	}
	
	public static User getUser(String uid){
		return umap.get(uid);
	}
	
	public static void updateUser(User user){
		User u = umap.get(user.getUid());
		u.setUsername(user.getUsername()+new Date());
		umap.put(user.getUid(), u);
	}
	
}
