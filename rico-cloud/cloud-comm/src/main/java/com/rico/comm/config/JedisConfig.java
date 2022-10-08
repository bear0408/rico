package com.rico.comm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
@Configuration
public class JedisConfig extends CachingConfigurerSupport {

    // 连接池对象
    @Autowired
    private static JedisPool jedisPool;

    @Value("${spring.redis.host:127.0.0.1}") // :redis.mkl.io
    private String host;
    @Value("${spring.redis.port:6379}") //:6379
    private Integer port;
    @Value("${spring.redis.password:}") // :caicaimima
    private String password;
    @Value("${spring.redis.database:0}")
    private Integer database;

    @Value("${spring.redis.jedis.pool.max-active:300}")
    private Integer maxActive;
    @Value("${spring.redis.jedis.pool.max-idle:100}")
    private Integer maxIdle;
    @Value("${spring.redis.jedis.pool.max-wait:-1}")
    private Long maxWait;
    @Value("${spring.redis.jedis.pool.min-idle:0}")
    private Integer minIdle;
    @Value("${spring.redis.timeout:0}")
    private Integer timeout;

    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMinIdle(minIdle);
        return jedisPoolConfig;
    }

    @Bean
    public JedisPool redisPoolFactory() {
        JedisPoolConfig jedisPoolConfig = jedisPoolConfig();
        return new JedisPool(jedisPoolConfig, host, port, timeout, password, database);
    }

}