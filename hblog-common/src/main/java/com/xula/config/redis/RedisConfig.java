package com.xula.config.redis;

import com.xula.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 配置
 * @author xla
 */
@Import(DataJedisProperties.class)
@Configuration
public class RedisConfig {

    /**
     * redis参数信息
     */
    @Autowired
    private DataJedisProperties dataJedisProperties;


    /**
     * 创建redis连接
     * @return
     */
    @Bean("jedisConnectionFactory")
    public RedisConnectionFactory getRedisConnectionFactory() {
        RedisStandaloneConfiguration redis = new RedisStandaloneConfiguration();
        redis.setPort(dataJedisProperties.getPort());
        redis.setPassword(dataJedisProperties.getPassword());
        redis.setHostName(dataJedisProperties.getHost());
        redis.setPort(dataJedisProperties.getPort());
        return new JedisConnectionFactory(redis);
    }

    /**
     * 配置redis模板
     * @param factory
     * @param <T>
     * @return
     */
    @Bean("redisTemplate")
    public <T> RedisTemplate<String, T> redisTemplate(@Qualifier("jedisConnectionFactory") RedisConnectionFactory factory) {
        RedisTemplate<String, T> template = new RedisTemplate<String, T>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());
        return template;
    }
}