package com.example.pet_hospital;

import com.example.pet_hospital.Entity.user;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class PetHospitalApplicationTests {

	@Autowired
	private RedisTemplate<String,Object> redisTemplate;

	@Test
	void testString(){
		redisTemplate.opsForValue().set("name","zhangsan");
		Object name = redisTemplate.opsForValue().get("name");
		System.out.println(name);
	}

	@Test
	void testSaveUser(){
		user u= new user();
		u.setUsername("root");
		u.setPassword("123456");
		redisTemplate.opsForValue().set("userdata",u);

		user ans= (user) redisTemplate.opsForValue().get("userdata");
		System.out.println(ans);
	}

}
