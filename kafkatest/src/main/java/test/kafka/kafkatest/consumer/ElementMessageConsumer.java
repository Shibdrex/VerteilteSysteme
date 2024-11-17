package test.kafka.kafkatest.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ElementMessageConsumer {

    @KafkaListener(topics = "elementMessage", groupId = "list-group",
    containerFactory = "kafkaElementListenerContainerFactory")
    public void listen(ElementMessage message) {
        System.out.println("Received message: " + message);
    }
}
