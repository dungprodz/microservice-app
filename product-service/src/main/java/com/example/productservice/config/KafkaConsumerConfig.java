package com.example.productservice.config;

import com.example.productservice.util.GroupKafka;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-server}")
    private String bootstrapServer;
    @Value("${spring.kafka.auto-commit-interval}")
    private String autoCommitInterval;
    @Value("${spring.kafka.session-timeout}")
    private String sessionTimeout;
    @Value("${spring.kafka.auto-offset-reset}")
    private String autoOffsetReset;
    @Value("${spring.kafka.max-poll-record}")
    private String maxPollRecord;
    @Value("${spring.kafka.enableUserPass}")
    private Boolean enableUserPass;
    @Value("${spring.kafka.userName}")
    private String userName;
    @Value("${spring.kafka.passWord}")
    private String passWord;
    @Value("${spring.kafka.encryptKey}")
    private String key;

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GroupKafka.ORDER_TOPIC_GROUP);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecord);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory(
            ConsumerFactory<String, String> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);

        // Cấu hình retry: 3 lần, delay 5 giây giữa mỗi lần
        FixedBackOff fixedBackOff = new FixedBackOff(5000L, 3L);
        // Handler xử lý lỗi và retry
        DefaultErrorHandler errorHandler = new DefaultErrorHandler((record, exception) -> {
            // Logic xử lý khi quá số lần retry (Recovery callback)
            System.err.println("Failed record: " + record);
            System.err.println("Exception: " + exception.getMessage());
            // Bạn có thể log, gửi cảnh báo, hoặc lưu record vào database để xử lý sau
        }, fixedBackOff);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //factory.setErrorHandler(new SeekToCurrentErrorHandler());
        return factory;
    }
}
