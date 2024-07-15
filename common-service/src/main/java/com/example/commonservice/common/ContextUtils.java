package com.example.commonservice.common;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
public class ContextUtils {
    private static ApplicationContext applicationContext;

    public ContextUtils(ApplicationContext applicationContext) {
        ContextUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static Executor getExecutor() {
        return (Executor) getBean("asyncExecutor", Executor.class);
    }
}