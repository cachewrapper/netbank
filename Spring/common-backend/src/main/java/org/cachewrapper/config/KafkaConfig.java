package org.cachewrapper.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.cachewrapper.event.BaseEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JacksonJsonDeserializer;
import org.springframework.kafka.support.serializer.JacksonJsonSerializer;

import java.util.Map;
import java.util.UUID;

@Configuration
@EnableKafka
public class KafkaConfig {

    private static final String KAFKA_BOOTSTRAP_HOST = "localhost:9092";

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BaseEvent<?>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BaseEvent<?>> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public KafkaTemplate<String, BaseEvent<?>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<String, BaseEvent<?>> producerFactory() {
        Map<String, Object> properties = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_HOST,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );

        JacksonJsonSerializer<BaseEvent<?>> jsonSerializer = new JacksonJsonSerializer<>();
        jsonSerializer.setAddTypeInfo(true);

        return new DefaultKafkaProducerFactory<>(properties, new StringSerializer(), jsonSerializer);
    }

    @Bean
    public ConsumerFactory<String, BaseEvent<?>> consumerFactory() {
        JacksonJsonDeserializer<BaseEvent<?>> jsonDeserializer = new JacksonJsonDeserializer<>(BaseEvent.class);
        jsonDeserializer.addTrustedPackages("*");
        jsonDeserializer.setUseTypeHeaders(true);

        Map<String, Object> properties = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_HOST,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class,
                ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString()
        );

        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), jsonDeserializer);
    }
}