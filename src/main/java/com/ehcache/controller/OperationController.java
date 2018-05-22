package com.ehcache.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
	public static Logger logger = LoggerFactory.getLogger(OperationController.class);
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private UserService userService;
	@Autowired
	private TrackService trackService;
	@Value("${server.port}")
	private int port;
	
	Gson gson = new Gson();
	
	private String device;
	
	// ---------------------------------------------------------------------
	/**
	 * 模拟并发请求ClassVariable
	 * @param threadNum 开启的线程数
	 * @return
	 */
	@RequestMapping(value = "/testClassVariable", method = RequestMethod.GET)
	public String testClassVariable(int threadNum){
		ExecutorService es = Executors.newFixedThreadPool(threadNum);
		final CyclicBarrier cb = new CyclicBarrier(threadNum);
		for (int i = 0; i < threadNum; i++) {
			es.execute(new Runnable() {
				@Override
				public void run() {
					String deviceid = UUID.randomUUID().toString().replace("-", "").substring(0,8);
					try {
						cb.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
					restTemplate.getForObject("http://127.0.0.1:"+port+"/o/classVariable?deviceid="+deviceid,String.class);
					deviceid = null;
				}
			});
		}
		es.shutdown();
		return "...";
	}
	
	/**
	 * 测试全局变量device在并发情况下值是否符合预期
	 * @param deviceid
	 * @return
	 */
	@RequestMapping(value = "/classVariable", method = RequestMethod.GET)
	public String classVariable(String deviceid){
		String roamid = "流程-"+UUID.randomUUID().toString().replace("-", "").substring(0,16)+"-";
		logger.info(roamid+"传入deviceid:"+deviceid);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		device = "device"+deviceid;
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info(roamid+"返回:"+device);
		return device;
	}
	
	// ---------------------------------------------------------------------
	/**
	 * 测试并发插入
	 * @param deviceid
	 * @return
	 */
	@RequestMapping(value = "/testBatchInsert", method = RequestMethod.GET)
	public String testBatchInsert(String deviceid){
		
		if (deviceid == null) {
			return "缺少deviceid";
		}else{
			Track param = new Track();
			param.setDeviceid(deviceid);
			boolean f = trackService.save(param);
			return "{\"rs\":"+f+"}";
		}
		
	}
	
	/**
	 * 测试并发查询
	 * @return
	 */
	@RequestMapping(value = "/threeDaysTrack", method = RequestMethod.GET)
	public String threeDaysTrack(){
		List<Track> tracks = trackService.getThreeDaysTrack();
		return gson.toJson(tracks);
	}
	// ---------------------------------------------------------------------
	
	
}
