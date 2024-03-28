package com.kuroko.heathyapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.JedisPooled;

@Configuration
@EnableCaching
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;
    @Value("${spring.data.redis.port}")
    private String port;

    @Bean
    public JedisPooled redisClient() {
        JedisPooled jedis = new JedisPooled(host, Integer.parseInt(port));
        jedis.set("foo", "bar");
        System.out.println(jedis.get("foo")); // prints "bar"
        return jedis;
    }
}
