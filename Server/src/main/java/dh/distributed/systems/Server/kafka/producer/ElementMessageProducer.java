package dh.distributed.systems.Server.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import dh.distributed.systems.Server.message.ElementMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ElementMessageProducer {

    private final KafkaTemplate<String, ElementMessage> kafkaElementTemplate;

    public void sendMessage(String topic, ElementMessage message) {
        kafkaElementTemplate.executeInTransaction(operations -> {
            operations.send(topic, message);
            return true;
        });
    }
}