package dh.distributed.systems.Server.websocket.controller;

import java.util.UUID;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import dh.distributed.systems.Server.kafka.producer.ElementMessageProducer;
import dh.distributed.systems.Server.kafka.producer.ListMessageProducer;
import dh.distributed.systems.Server.message.ElementMessage;
import dh.distributed.systems.Server.message.ListMessage;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class WSListMessageController {

    private final ElementMessageProducer elementProducer;

    private final ListMessageProducer listProducer;
    // UUID using
    @MessageMapping("/list")
    public void listMessage(ListMessage message) throws Exception {
        message.setMessageID(UUID.randomUUID());
        listProducer.sendMessage("todo-list-send", message);
    }

    @MessageMapping("/element")
    public void elementMessage(ElementMessage message) throws Exception {
        message.setMessageID(UUID.randomUUID());
        elementProducer.sendMessage("todo-element-send", message);
    }
}
