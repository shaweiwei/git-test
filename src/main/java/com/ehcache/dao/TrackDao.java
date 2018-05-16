package com.ehcache.dao;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ehcache.entity.Track;

@Repository
public class TrackDao {
	
//	@Resource
//	SqlSessionFactory sessionFactory;
//	
//	private SqlSession session = null;
//	
//	public SqlSession getSession(){
//		if (session == null) {
//			session = sessionFactory.openSession();
//		}
//		return session;
//	}
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public Track findById(String uid){
		Track track = sqlSession.selectOne("com.ehcache.entity.Track.selectOne", uid);
		return track;
	}

	public void del(String uid) {
		sqlSession.delete("com.ehcache.entity.Track.delete", uid);
	}

	public void update(Track track) {
		sqlSession.update("com.ehcache.entity.Track.update", track);
	}

	public boolean save(Track track) {
		// TODO Auto-generated method stub
//		int a = getSession().insert("com.ehcache.entity.Track.insert", track);
		int a = sqlSession.insert("com.ehcache.entity.Track.insert", track);
		if (a > 0) {
			return true;
		}else{
			return false;
		}
	}

	public List<Track> getThreeDaysTrack() {
		return sqlSession.selectList("com.ehcache.entity.Track.getThreeDaysTrack", null);
	}

}
