package dh.distributed.systems.Server.kafka.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import dh.distributed.systems.Server.ServerApplication;
import dh.distributed.systems.Server.message.ElementAnswer;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ElementAnswerConsumer {

    private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(
        topics = "todo-element-answer",
        containerFactory = "kafkaElementAnswerListenerContainerFactory")
    public void listen(ElementAnswer answer) {
        log.info("Received ElementAnswer from Kafka: {}", answer);
        messagingTemplate.convertAndSend("/topic/element-answer", answer);
    }
}
