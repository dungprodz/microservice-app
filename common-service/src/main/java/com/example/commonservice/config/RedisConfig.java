//package com.example.commonservice.config;
//
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisSentinelConfiguration;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.GenericToStringSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//
//@Configuration
//@EnableConfigurationProperties({RedisProperties.class})
//public class RedisConfig {
//    private final RedisProperties redisProperties;
//
//    public RedisConfig(RedisProperties redisProperties) {
//        this.redisProperties = redisProperties;
//    }
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        RedisProperties.Pool pool = this.redisProperties.getJedis().getPool();
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
//        poolConfig.setMaxTotal(pool.getMaxActive());
//        poolConfig.setMaxIdle(pool.getMaxIdle());
//        poolConfig.setMinIdle(pool.getMinIdle());
//        poolConfig.setTestOnBorrow(false);
//        poolConfig.setTestOnReturn(false);
//        RedisProperties.Sentinel sentinel = this.redisProperties.getSentinel();
//        RedisSentinelConfiguration sentinelConfiguration = (new RedisSentinelConfiguration()).master(sentinel.getMaster());
//        sentinel.getNodes().forEach((node) -> {
//            sentinelConfiguration.sentinel(node, this.redisProperties.getPort());
//        });
//        sentinelConfiguration.setSentinelPassword(sentinel.getPassword());
//        sentinelConfiguration.setPassword(this.redisProperties.getPassword());
//        sentinelConfiguration.setDatabase(this.redisProperties.getDatabase());
//        return new JedisConnectionFactory(sentinelConfiguration, poolConfig);
//    }
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(this.redisConnectionFactory());
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericToStringSerializer<>(String.class));
//        return template;
//    }
//}
