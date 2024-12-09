package dh.distributed.systems.Server.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import dh.distributed.systems.Server.message.ListMessage;
import lombok.AllArgsConstructor;

/**
 * Class deals with sending {@link ListMessage}s to the List-Service over
 * kafka.
 */
@Component
@AllArgsConstructor
public class ListMessageProducer {

    private final KafkaTemplate<String, ListMessage> KafkaListTemplate;

    public void sendMessage(String topic, ListMessage message) {
        KafkaListTemplate.executeInTransaction(operations -> { // execute in transaction to ensure idempotence
            operations.send(topic, message);
            return true;
        });
    }
}
