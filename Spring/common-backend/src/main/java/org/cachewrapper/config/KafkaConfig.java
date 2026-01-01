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

/**
 * Kafka configuration for producing and consuming events in the system.
 * <p>
 * This configuration sets up:
 * <ul>
 *     <li>ProducerFactory and KafkaTemplate for publishing {@link BaseEvent} instances.</li>
 *     <li>ConsumerFactory and ConcurrentKafkaListenerContainerFactory for consuming {@link BaseEvent} messages.</li>
 * </ul>
 * The configuration uses JSON serialization/deserialization via Jackson
 * and includes type headers to support polymorphic {@link BaseEvent} types.
 * </p>
 */
@Configuration
@EnableKafka
public class KafkaConfig {

    private static final String KAFKA_BOOTSTRAP_HOST = "localhost:9092";

    /**
     * Configures the Kafka listener container factory for consuming events.
     * This factory is used by all {@link org.springframework.kafka.annotation.KafkaListener} annotations.
     *
     * @return the Kafka listener container factory
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BaseEvent<?>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, BaseEvent<?>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    /**
     * Provides a Kafka template for sending events to Kafka topics.
     *
     * @return KafkaTemplate configured for {@link BaseEvent} messages
     */
    @Bean
    public KafkaTemplate<String, BaseEvent<?>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    /**
     * Configures the producer factory for serializing {@link BaseEvent} messages as JSON.
     *
     * @return ProducerFactory for BaseEvent messages
     */
    @Bean
    public ProducerFactory<String, BaseEvent<?>> producerFactory() {
        Map<String, Object> properties = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_HOST,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class
        );

        JacksonJsonSerializer<BaseEvent<?>> jsonSerializer = new JacksonJsonSerializer<>();
        jsonSerializer.setAddTypeInfo(true); // Include type info for polymorphic deserialization

        return new DefaultKafkaProducerFactory<>(properties, new StringSerializer(), jsonSerializer);
    }

    /**
     * Configures the consumer factory for deserializing {@link BaseEvent} messages from JSON.
     *
     * @return ConsumerFactory for BaseEvent messages
     */
    @Bean
    public ConsumerFactory<String, BaseEvent<?>> consumerFactory() {
        JacksonJsonDeserializer<BaseEvent<?>> jsonDeserializer = new JacksonJsonDeserializer<>(BaseEvent.class);
        jsonDeserializer.addTrustedPackages("*"); // Trust all packages for polymorphic deserialization
        jsonDeserializer.setUseTypeHeaders(true);

        Map<String, Object> properties = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_HOST,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JacksonJsonDeserializer.class,
                ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString() // Random group ID for isolation
        );

        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), jsonDeserializer);
    }
}