package com.ehcache.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ehcache.entity.Track;
import com.ehcache.entity.User;
import com.ehcache.factory.CacheManagerFactory;
import com.ehcache.factory.UserFactory;
import com.ehcache.factory.WebConfig;
import com.ehcache.service.TrackService;
import com.ehcache.service.UserService;
import com.google.gson.Gson;

import net.sf.ehcache.Element;


@RestController
@RequestMapping("/o")
public class OperationController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private TrackService trackService;
	
	Gson gson = new Gson();
	
	CacheManagerFactory cmf = CacheManagerFactory.getInstance();
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test(HttpServletRequest request){
		
		// 保存一个新用户
		String uid = userService.save(UserFactory.createUser());
		User user = userService.getUserById(uid);
		user.setUsername("xiaoli");
		userService.update(user);
		
		// 查询该用户
		System.out.println(gson.toJson(user, User.class));
		/*System.out.println();
		// 再查询该用户
		User user = userService.getUserById(uid);
		System.out.println(gson.toJson(user, User.class));
		System.out.println();
		// 更新该用户
		userService.update(user);
		// 更新好了再查询该用户
		System.out.println(gson.toJson(userService.getUserById(uid), User.class));
		System.out.println();
		// 删除该用户
		userService.del(uid);
		System.out.println();
		// 删除好了再查询该用户
		System.out.println(gson.toJson(userService.getUserById(uid), User.class));*/
		
		
		// 再保存一个新用户
//		String uid1 = userService.save(UserFactory.createUser());
		
		
		return uid;
	}
	
	
	@RequestMapping(value = "/test1", method = RequestMethod.GET)
	public String test1(HttpServletRequest request,String key){
		
		String res = "";

        Element element = cmf.getElement("userCache", "map");
        if(element == null){
            Map<String, String> map = new HashMap<String, String>();
            map.put(key, key);
            cmf.setElement("userCache", new Element("map", map));
        }else{
            Map<String, String> map = (Map<String, String>) element.getValue();
            res = map.get(key);
            if(res == null){
                map.put(key, key);
                // 多次测试发现，存在同名Element是，重复put的是无法复制的，因此当遇到两个节点同步不上的时候，先remove后put。 
                cmf.getCache("userCache").remove("map");
                cmf.setElement("userCache", new Element("map", map));
                res = "0;null";
            }
        }
        return res;
	}
	
	@RequestMapping(value = "/testBatchInsert", method = RequestMethod.GET)
	public String testBatchInsert(String deviceid){
		Track param = new Track();
		param.setDeviceid(deviceid);
		boolean f = trackService.save(param);
		return "{\"rs\":"+f+"}";
	}
	
}
