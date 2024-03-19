package com.example.pet_hospital;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.hutool.json.ObjectMapper;
import com.example.pet_hospital.Entity.user;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PetHospitalStringTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;


	@Test
	void testString(){
		stringRedisTemplate.opsForValue().set("name","zhangsan");
		Object name = stringRedisTemplate.opsForValue().get("name");
		System.out.println(name);
	}

	@Test
	void testSaveUser(){
		user u=new user();
		u.setUsername("tester");
		u.setPassword("nonename");
		stringRedisTemplate.opsForValue().set("newuser", JSONUtil.toJsonStr(u));
		Object data = stringRedisTemplate.opsForValue().get("newuser");
		System.out.println(data);
	}

	@Test
	void testHash(){
		stringRedisTemplate.opsForHash().put("user:400","name","tester");
		stringRedisTemplate.opsForHash().put("user:400","gender","F");
		stringRedisTemplate.opsForHash().put("user:400","address","sh");
		Map<Object,Object> m=new HashMap<>();
		m=stringRedisTemplate.opsForHash().entries("user:400");
		System.out.println(m);
	}


}
