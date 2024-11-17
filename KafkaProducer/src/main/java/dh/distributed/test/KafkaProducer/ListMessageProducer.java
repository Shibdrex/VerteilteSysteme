package dh.distributed.test.KafkaProducer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ListMessageProducer {

    private final KafkaTemplate<String, ListMessage> KafkaListTemplate;

    public void sendMessage(String topic, ListMessage message) {
        KafkaListTemplate.send(topic, message);
    }
}
