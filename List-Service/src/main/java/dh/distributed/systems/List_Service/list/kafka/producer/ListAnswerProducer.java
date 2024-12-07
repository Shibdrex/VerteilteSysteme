package dh.distributed.systems.List_Service.list.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import dh.distributed.systems.List_Service.list.model.ListAnswer;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ListAnswerProducer {
    
    private final KafkaTemplate<String, ListAnswer> kafkaListTemplate;

    public void sendMessage(String topic, ListAnswer answer) {
        kafkaListTemplate.executeInTransaction(operations -> {
            operations.send(topic, answer);
            return true;
        });
    }
}
