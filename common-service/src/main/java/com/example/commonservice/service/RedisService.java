package com.example.commonservice.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface RedisService {
    void putValue(String topic, Map<String, String> maps);

    <T> Optional<T> getValue(String topic, String key, Class<T> clazz);

    <T> Optional<T> getValue(String topic, String key);

    <T> List<T> getValue(String topic, Class<T> clazz);

    <T> List<T> getValue(String topic);

    void remove(String topic);

    void remove(String topic, List<String> keys);

    boolean hasCacheValue(String topic);

    Set<String> keys(String topic);

    boolean isNotExisted(String topic, String key);

    boolean isExisted(String topic, String key);
}
