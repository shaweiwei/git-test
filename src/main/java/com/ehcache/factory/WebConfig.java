package com.ehcache.factory;

import java.util.Properties;

public class WebConfig {

	private static WebConfig webConfig;
	
	private WebConfig(){
		// 5000并发 持续3小时
		// 
	}
	
	public static WebConfig getInstance(){
		if (webConfig == null) {
			webConfig = new WebConfig();
			System.out.println("new ...");
		}
		return webConfig;
	}
	
	
}
