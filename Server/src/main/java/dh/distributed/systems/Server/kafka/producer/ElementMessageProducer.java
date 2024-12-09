package dh.distributed.systems.Server.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import dh.distributed.systems.Server.message.ElementMessage;
import lombok.AllArgsConstructor;

/**
 * Class deals with sending {@link ElementMessage}s to the List-Service over
 * kafka.
 */
@Component
@AllArgsConstructor
public class ElementMessageProducer {

    private final KafkaTemplate<String, ElementMessage> kafkaElementTemplate;

    public void sendMessage(String topic, ElementMessage message) {
        kafkaElementTemplate.executeInTransaction(operations -> { // execute in transaction to ensure idempotence
            operations.send(topic, message);
            return true;
        });
    }
}