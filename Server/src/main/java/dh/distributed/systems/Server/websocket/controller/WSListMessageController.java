package dh.distributed.systems.Server.websocket.controller;

import java.util.UUID;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import dh.distributed.systems.Server.kafka.producer.ElementMessageProducer;
import dh.distributed.systems.Server.kafka.producer.ListMessageProducer;
import dh.distributed.systems.Server.message.ElementMessage;
import dh.distributed.systems.Server.message.ListMessage;
import lombok.AllArgsConstructor;

/**
 * Handles incoming websocket messages and sending kafka messages.
 */
@AllArgsConstructor
@Controller
public class WSListMessageController {

    private final ElementMessageProducer elementProducer;

    private final ListMessageProducer listProducer;

    /**
     * Method receives websocket messages from the list-topic then generates a UUID
     * to add to the
     * messages and send it to the list-send-kafka-topic.
     * 
     * @param message to be forwarded to kafka
     * @throws Exception if sending the message to kafka fails
     */
    @MessageMapping("/list")
    public void listMessage(ListMessage message) throws Exception {
        message.setMessageID(UUID.randomUUID());
        listProducer.sendMessage("todo-list-send", message);
    }

    /**
     * Method receives websocket messages from the element-topic then generates a
     * UUID to add to the messages and send it to the element-send-kafka-topic.
     * 
     * @param message to be forwarded to kafka
     * @throws Exception if sending the message to kafka fails
     */
    @MessageMapping("/element")
    public void elementMessage(ElementMessage message) throws Exception {
        message.setMessageID(UUID.randomUUID());
        elementProducer.sendMessage("todo-element-send", message);
    }
}
