package com.bridgelabz.fundookeep.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
	
	private RedisTemplate<String, Long> redisTemplet;

	private HashOperations<String, String, Long> hashOperation;
	
	@Autowired
	public RedisService(RedisTemplate<String, Long> redisTemplet) {
		this.redisTemplet = redisTemplet;
		hashOperation = redisTemplet.opsForHash();
	}
	
	public void putToken(String token,Long id) {
		hashOperation.put("token", token, id);
	}
	
	public Long getToken(String token) {
		return hashOperation.get("token", token);
	}
	
}
