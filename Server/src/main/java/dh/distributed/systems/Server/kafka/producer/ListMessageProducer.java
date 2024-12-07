package dh.distributed.systems.Server.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import dh.distributed.systems.Server.message.ListMessage;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ListMessageProducer {

    private final KafkaTemplate<String, ListMessage> KafkaListTemplate;

    public void sendMessage(String topic, ListMessage message) {
        KafkaListTemplate.executeInTransaction(operations -> {
            operations.send(topic, message);
            return true;
        });
    }
}
