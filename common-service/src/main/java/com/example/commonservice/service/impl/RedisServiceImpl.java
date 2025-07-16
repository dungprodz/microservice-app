package com.example.commonservice.service.impl;

import com.example.commonservice.service.RedisService;
import com.example.commonservice.ulti.TopicModelMapping;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final HashOperations<String, String, String> hashOperation;

    public RedisServiceImpl(RedisTemplate<String, String> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
        this.hashOperation = redisTemplate.opsForHash();
    }

    public void putValue(String topic, Map<String, String> maps) {
        this.hashOperation.putAll(topic, maps);
    }

    public <T> Optional<T> getValue(String topic, String key, Class<T> clazz) {
        try {
            String jsonValue = (String) this.hashOperation.get(topic, key);
            if (!StringUtils.hasText(jsonValue)) {
                log.warn("No value with topic {} and key {}", topic, key);
                return Optional.empty();
            } else {
                return Optional.ofNullable(this.objectMapper.readValue(jsonValue, clazz));
            }
        } catch (Exception var5) {
            log.error("Error get value with topic {} and key {}, error message {}", topic, key, var5.getMessage());
            return Optional.empty();
        }
    }

    public <T> Optional<T> getValue(String topic, String key) {
        return (Optional<T>) this.getValue(topic, key, TopicModelMapping.get(topic));
    }

    public void remove(String topic) {
        this.redisTemplate.delete(topic);
    }

    public void remove(String topic, List<String> keys) {
        this.hashOperation.delete(topic, keys.toArray());
    }

    public boolean hasCacheValue(String topic) {
        return StringUtils.hasText((String) this.hashOperation.randomKey(topic));
    }

    public Set<String> keys(String topic) {
        return this.hashOperation.keys(topic);
    }

    public boolean isNotExisted(String topic, String key) {
        return this.getValue(topic, key).isEmpty();
    }

    public boolean isExisted(String topic, String key) {
        return this.getValue(topic, key).isPresent();
    }

    public <T> List<T> getValue(String topic, Class<T> clazz) {
        Map<String, String> allValues = this.hashOperation.entries(topic);
        if (ObjectUtils.isEmpty(allValues)) {
            return new ArrayList<>();
        } else {
            List<T> result = new ArrayList<>();

            for (String value : allValues.values()) {
                try {
                    result.add(this.objectMapper.readValue(value, clazz));
                } catch (JsonProcessingException var8) {
                    log.error("Error get value with topic {}", topic, var8);
                }
            }

            return Collections.unmodifiableList(result);
        }
    }

    public <T> List<T> getValue(String topic) {
        return (List<T>) this.getValue(topic, TopicModelMapping.get(topic));
    }
}