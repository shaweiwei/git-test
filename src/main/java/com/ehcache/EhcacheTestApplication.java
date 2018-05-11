package com.ehcache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.ehcache")//扫描组件
@ServletComponentScan(basePackages="ehcache")//扫描拦截器，过滤器
@EnableCaching
public class EhcacheTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(EhcacheTestApplication.class, args);
	}
}
