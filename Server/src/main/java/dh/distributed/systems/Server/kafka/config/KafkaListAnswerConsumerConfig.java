package dh.distributed.systems.Server.kafka.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import dh.distributed.systems.Server.kafka.serializer.ListAnswerDeSerializer;
import dh.distributed.systems.Server.message.ListAnswer;

@Configuration
public class KafkaListAnswerConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupID;

    @Value("${spring.kafka.consumer.client-id.todo-list}")
    private String clientID;

    @Value("${spring.kafka.consumer.isolation-level}")
    private String isolationLevel;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String autoOffsetReset;

    @Bean
    public ConsumerFactory<String, ListAnswer> listAnswerConsumerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, groupID);
        configProps.put(ConsumerConfig.CLIENT_ID_CONFIG, clientID);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ListAnswerDeSerializer.class);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        configProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, isolationLevel);
        configProps.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10000);
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ListAnswer> kafkaListAnswerListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ListAnswer> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(listAnswerConsumerFactory());
        factory.setConcurrency(3);
        return factory;
    }
}
