package com.ehcache.factory;

import java.io.InputStream;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheManagerFactory {

	private CacheManager manager;
    private static CacheManagerFactory factory = new CacheManagerFactory();
    private final static String EHCACHEFILE = "/ehcache.xml";

    private CacheManagerFactory() {
    }

    public static CacheManagerFactory getInstance() {
        return factory;
    }

    public CacheManager getCacheManager() {
        if (manager == null) {
            InputStream is = this.getClass().getResourceAsStream(EHCACHEFILE);
            manager = CacheManager.create(is);
        }
        return manager;
    }

    public Cache getCache(String cache) {
        return getCacheManager().getCache(cache);
    }

    public void setCache(Cache cache) {
        getCacheManager().addCache(cache);
    }

    public void setCache(String cache) {
        getCacheManager().addCache(cache);
    }

    public Element getElement(String cacheName, String key) {
        if (getCache(cacheName) == null)
            setCache(cacheName);
        return getCache(cacheName).get(key);
    }

    public void setElement(String cache, Element element) {
        if (getCache(cache) == null)
            setCache(cache);
        getCache(cache).put(element);
    }

    public Boolean continaElementKey(String cacheName, String key) {
        if (getCache(cacheName) == null)
            setCache(cacheName);
        return getCache(cacheName).isKeyInCache(key);
    }
}
