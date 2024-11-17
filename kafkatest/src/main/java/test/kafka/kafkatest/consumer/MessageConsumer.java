package test.kafka.kafkatest.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "listelement", groupId = "list-group",
    containerFactory = "kafkaListenerContainerFactory")
    public void listen(ListElement message) {
        System.out.println("Received message: " + message);
    }
}
