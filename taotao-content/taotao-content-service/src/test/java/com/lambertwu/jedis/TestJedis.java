package com.lambertwu.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestJedis {

	@Test
	public void testJedis() throws Exception {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set("jedis-key", "13234");
		String result = jedis.get("jedis-key");
		System.out.println(result);
		
		jedis.close();
	}
	
	@Test
	public void testJedisPool() throws Exception {
		JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get("jedis-key");
		System.out.println(result);
		jedis.close();
		
		jedisPool.close();
	}
}
