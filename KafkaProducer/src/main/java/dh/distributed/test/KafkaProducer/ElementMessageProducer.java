package dh.distributed.test.KafkaProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ElementMessageProducer {

    private final KafkaTemplate<String, ElementMessage> kafkaElementTemplate;

    public void sendMessage(String topic, ElementMessage message) {
        kafkaElementTemplate.send(topic, message);
    }
}
