/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.hiveview.admin.commom.security.session;

import com.hiveview.admin.commom.RequestContextHolder;
import com.hiveview.cache.redis.RedisService;
import com.hiveview.base.util.serializer.ObjectUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Set;

/**
 * 自定义授权缓存管理类
 */
@Service
public class JedisCacheManager implements CacheManager {

	private static final Logger logger= LoggerFactory.getLogger(JedisCacheManager.class);
    
	@Autowired
	private RedisService redisService;
	
	private String cacheKeyPrefix = "shiro_cache_";
	
	@Override
	public <K, V> Cache<K, V> getCache(String name) throws CacheException {
		return new JedisCache<K, V>(cacheKeyPrefix + name);
	}

	public String getCacheKeyPrefix() {
		return cacheKeyPrefix;
	}

	public void setCacheKeyPrefix(String cacheKeyPrefix) {
		this.cacheKeyPrefix = cacheKeyPrefix;
	}
	
	/**
	 * 自定义授权缓存管理类
	 * @author ThinkGem
	 * @version 2014-7-20
	 */
	public class JedisCache<K, V> implements Cache<K, V> {

		private String cacheKeyName = null;

		public JedisCache(String cacheKeyName) {
			this.cacheKeyName = cacheKeyName;
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public V get(K key) throws CacheException {
			if (key == null){
				return null;
			}
			
			V v = null;
			HttpServletRequest request = RequestContextHolder.getRequest();
			if (request != null){
				v = (V)request.getAttribute(cacheKeyName);
				if (v != null){
					return v;
				}
			}
			byte[] result=redisService.getHashBytes(cacheKeyName,key.toString());
			V value=null;
			if(null != result){
				value= (V) ObjectUtils.unserialize(result);
			}

			if (request != null && value != null){
				request.setAttribute(cacheKeyName, value);
			}
			return value;
		}

		@Override
		public V put(K key, V value) throws CacheException {
			if (key == null){
				return null;
			}
			redisService.setHashBytes(cacheKeyName,key.toString(), ObjectUtils.serialize(value),0);
			return value;
		}

		@SuppressWarnings("unchecked")
		@Override
		public V remove(K key) throws CacheException {
			V value = (V) redisService.mapRemove(cacheKeyName, key.toString());
			return value;
		}

		@Override
		public void clear() throws CacheException {
			redisService.deleteByKey(cacheKeyName);
		}

		@Override
		public int size() {
			int size=redisService.getHashLen(cacheKeyName).intValue();
			return size;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Set<K> keys() {
			Set<K> keys=(Set<K>) redisService.getObjectSet(cacheKeyName, Object.class);
			return keys;
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<V> values() {
			Collection<V> vals = (Collection<V>) redisService.getCollection(cacheKeyName, Object.class);
			return vals;
		}
	}
}
