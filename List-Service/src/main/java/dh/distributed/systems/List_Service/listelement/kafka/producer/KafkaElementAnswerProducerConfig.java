package dh.distributed.systems.List_Service.listelement.kafka.producer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import dh.distributed.systems.List_Service.listelement.model.ElementAnswer;

@Configuration
public class KafkaElementAnswerProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${spring.kafka.producer.transaction-id-prefix}")
    private String transactionIdPrefix;


    @Bean
    @Primary
    public ProducerFactory<String, ElementAnswer> elementProducerFactory() throws UnknownHostException {
        Map<String, Object> configProps = new HashMap<>();
        String hostname = InetAddress.getLocalHost().getHostName();
        String transactionalId = transactionIdPrefix + "-" + hostname + "-element";

        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionalId);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ElementAnswerSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    @Bean
    public KafkaTemplate<String, ElementAnswer> kafkaElementTemplate() throws UnknownHostException {
        KafkaTemplate<String, ElementAnswer> template = new KafkaTemplate<>(elementProducerFactory());
        String hostname = InetAddress.getLocalHost().getHostName();
        String transactionalIdPrefixWithHostname = transactionIdPrefix + "-" + hostname;
        template.setTransactionIdPrefix(transactionalIdPrefixWithHostname);
        return template;
    }
}