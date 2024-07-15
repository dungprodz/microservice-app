package com.example.commonservice.util;

import com.example.commonservice.model.Product;

import java.util.HashMap;
import java.util.Map;

public class TopicModelMapping {
    private static final Map<String, Class<?>> MAPPING = new HashMap();

    private TopicModelMapping() {
    }

    public static Class<?> get(String topic) {
        return (Class) MAPPING.get(topic);
    }

    static {
        MAPPING.put("CATEGORIES_BUSINESS_TYPE", Product.class);
    }
}
