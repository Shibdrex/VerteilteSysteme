package dh.distributed.systems.Server.kafka.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import dh.distributed.systems.Server.kafka.serializer.ListMessageSerializer;
import dh.distributed.systems.Server.message.ListMessage;

@Configuration
public class KafkaListMessageProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.producer.transaction-id-prefix}")
    private String transactionIdPrefix;

    @Autowired
    private ListMessageSerializer listMessageSerializer;

    @Bean
    public ProducerFactory<String, ListMessage> listProducerFactory() throws UnknownHostException {
        Map<String, Object> configProps = new HashMap<>();
        String hostname = InetAddress.getLocalHost().getHostName();
        String transactionalId = transactionIdPrefix + "-" + hostname + "-list";

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionalId);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, listMessageSerializer.getClass());
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), listMessageSerializer);
    }

    @Bean
    public KafkaTemplate<String, ListMessage> kafkaListTemplate() throws UnknownHostException {
        KafkaTemplate<String, ListMessage> template = new KafkaTemplate<>(listProducerFactory());
        String hostname = InetAddress.getLocalHost().getHostName();
        String transactionalIdPrefixWithHostname = transactionIdPrefix + "-" + hostname;
        template.setTransactionIdPrefix(transactionalIdPrefixWithHostname);
        return template;
    }
}
