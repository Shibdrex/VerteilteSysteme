package dh.distributed.systems.Server.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import dh.distributed.systems.Server.ServerApplication;
import dh.distributed.systems.Server.message.ListAnswer;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ListAnswerConsumer {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(
        topics = "todo-list-answer",
        containerFactory = "kafkaListAnswerListenerContainerFactory")
    public void listen(ListAnswer answer) {     
        log.info("Received ListAnswer from Kafka: {}", answer);
        messagingTemplate.convertAndSend("/topic/list-answer", answer);
    }
}