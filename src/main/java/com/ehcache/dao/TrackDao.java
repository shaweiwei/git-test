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

import com.ehcache.entity.Track;

@Repository
public class TrackDao {
	
	@Resource
	SqlSessionFactory sessionFactory;
	
	private SqlSession session = null;
	
	public SqlSession getSession(){
		if (session == null) {
			session = sessionFactory.openSession();
		}
		return session;
	}
	
	public Track findById(String uid){
		Track track = getSession().selectOne("com.ehcache.entity.Track.selectOne", uid);
		return track;
	}

	public void del(String uid) {
		getSession().delete("com.ehcache.entity.Track.delete", uid);
	}

	public void update(Track track) {
		getSession().update("com.ehcache.entity.Track.update", track);
	}

	public boolean save(Track track) {
		// TODO Auto-generated method stub
		int a = getSession().insert("com.ehcache.entity.Track.insert", track);
		if (a > 0) {
			return true;
		}else{
			return false;
		}
	}

}
