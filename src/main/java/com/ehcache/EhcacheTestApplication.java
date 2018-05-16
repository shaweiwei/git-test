package com.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages="com.ehcache")//扫描组件
@ServletComponentScan(basePackages="ehcache")//扫描拦截器，过滤器
@EnableCaching
public class EhcacheTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EhcacheTestApplication.class, args);
	}
	
	@Bean
	public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setConnectionRequestTimeout(100000);
        httpRequestFactory.setConnectTimeout(100000);
        httpRequestFactory.setReadTimeout(100000);
	    return new RestTemplate(httpRequestFactory);
	}
}
