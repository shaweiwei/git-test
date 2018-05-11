package com.ehcache.dao;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.ehcache.entity.User;
import com.ehcache.factory.UserFactory;

@Repository
public class UserDao {
	
	@Resource
	SqlSessionFactory sessionFactory;
	
	private SqlSession session = null;
	
	public SqlSession getSession(){
		if (session == null) {
			session = sessionFactory.openSession();
		}
		return session;
	}
	
	public User findById(String uid){
		User user = getSession().selectOne("com.ehcache.entity.User.selectOne", uid);
		return user;
	}

	public void del(String uid) {
		getSession().delete("com.ehcache.entity.User.delete", uid);
	}

	public void update(User user) {
		getSession().update("com.ehcache.entity.User.update", user);
	}

	public String save(User user) {
		// TODO Auto-generated method stub
		getSession().insert("com.ehcache.entity.User.insert", user);
		return user.getUid();
	}

}
