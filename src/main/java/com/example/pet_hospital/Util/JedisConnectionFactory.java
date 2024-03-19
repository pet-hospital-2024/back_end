package com.example.pet_hospital.Util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisConnectionFactory {

    // 连接池配置
    // ...
    private static final JedisPool jedisPool;
    static {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(8);
        poolConfig.setMaxIdle(8);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxWaitMillis(1000);

        jedisPool = new JedisPool(poolConfig,"wxl475.cn",6382,1000,"wxl5211314");
    }

    public static Jedis getJedis(){
        return jedisPool.getResource();
    }

}
