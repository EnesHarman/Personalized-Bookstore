package com.etrade.product.core.config.kafka;

import com.etrade.product.core.config.kafka.events.ProductEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
//    @Bean
//    public NewTopic topic() {
//        return TopicBuilder.name("product-topic")
//                .partitions(2)
//                .replicas(1)
//                .build();
//    }

    @Value(value = "${kafka.bootstrap-server}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, ProductEvent> producerFactory(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    @Bean
    public KafkaTemplate<String, ProductEvent> kafkaTemplate(){
        return new KafkaTemplate<String, ProductEvent>(producerFactory());
    }
}
