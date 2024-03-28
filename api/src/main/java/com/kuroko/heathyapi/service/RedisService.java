package com.kuroko.heathyapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPooled;

@Service
public class RedisService {
    @Autowired
    private JedisPooled jedis;

    public String getValue(String key) {
        return jedis.get(key);
    }

    // set value
    public String setValue(String key, String value) {
        return jedis.set(key, value);
    }

    public void deleteValue(String key) {
        jedis.del(key);
    }

}
