package com.hiveview.config.redis;

import com.hiveview.cache.redis.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

import java.util.Arrays;

/**
 * Created by leo on 2018/1/30.
 * redis缓存配置
 */
@Configuration
public class RedisConfig {

    @Value("${redis.pool.host}")
    private String host;

    @Value("${redis.pool.port}")
    private int port;

    @Value("${redis.pool.timeout}")
    private int timeout;

    @Value("${redis.pool.password}")
    private String password;


    @Value("${redis.pool.minIdle}")
    private int minIdle;

    @Value("${redis.pool.maxIdle}")
    private int maxIdle;

    @Value("${redis.pool.maxTotal}")
    private int maxTotal;

    @Value("${redis.maxWaitMillis}")
    private long maxWaitMillis;

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }

    @Bean
    public ShardedJedisPool shardedJedisPool(){
        JedisShardInfo shardInfo = new JedisShardInfo(host, port, timeout);
        shardInfo.setPassword(password);
        ShardedJedisPool pool = new ShardedJedisPool(jedisPoolConfig(), Arrays.asList(shardInfo));
        return pool;
    }

    @Bean
    public RedisService redisService(){
        RedisService redisService = new RedisService();
        redisService.setShardedJedisPool(shardedJedisPool());
        return redisService;
    }
}
