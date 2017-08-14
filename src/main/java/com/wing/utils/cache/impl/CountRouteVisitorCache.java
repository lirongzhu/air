
// ~ Package Declaration
// ==================================================

package com.wing.utils.cache.impl;

import java.io.InputStream;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wing.utils.cache.DataCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

// ~ Comments
// ==================================================

public class CountRouteVisitorCache implements DataCache{

	// ~ Static Fields
	// ==================================================

	private static final Logger logger = LogManager.getLogger(CountRouteVisitorCache.class.getName());
	
	// ~ Fields
	// ==================================================

	private InputStream is = this.getClass().getClassLoader().getResourceAsStream("/ehcache-spring.xml");

	private final CacheManager cacheManager = CacheManager.create(is);
	
	private final Cache cache = cacheManager.getCache("countRouteVisitor");
	
	// ~ Constructors
	// ==================================================

	// ~ Methods
	// ==================================================

	public boolean put(String key, Object value){
		
		try{
			
			Element element = new Element(key, value);
			cache.put(element);
			
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	public Object get(String key){
		
		Element element = cache.get(key);
		
		return element == null ? null : element.getValue();
	}
	
	public boolean remove(String key){
		return cache.remove(key);
	}
	
	public String getAll(){
		
		String returnStr = "";
		List<String> keys = (List<String>)cache.getKeys();
		
		for(String key : keys){
			returnStr += "key :" + key + " value : " + cache.get(key);
			logger.debug("key :" + key + " value : " + cache.get(key));
		}
		
		return returnStr;
	}
}
