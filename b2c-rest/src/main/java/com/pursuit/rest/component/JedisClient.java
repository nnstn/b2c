package com.pursuit.rest.component;

public interface JedisClient {
	public String set(final String key, String value) ;
	public String get(final String key);
	public Boolean exists(final String key);
	public Long del(String key);
	
	public Long hset(String key, String item, String value);
	public String hget(String key, String item);
	public Long hdel(String key,String... fields);
	
	public Long decr(String key);
	public Long incr(String key);
	public Long expire(final String key, final int seconds);
	public Long ttl(String key);
	
}
