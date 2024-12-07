package dh.distributed.systems.List_Service.listelement.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import dh.distributed.systems.List_Service.listelement.model.ElementAnswer;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ElementAnswerProducer {
    
    private final KafkaTemplate<String, ElementAnswer> kafkaElementTemplate;

    public void sendMessage(String topic, ElementAnswer message) {
        kafkaElementTemplate.executeInTransaction(operations -> {
            operations.send(topic, message);
            return true;
        });
    }
}
