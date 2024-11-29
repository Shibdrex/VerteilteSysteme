package dh.distributed.systems.Server.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import dh.distributed.systems.Server.kafka.producer.ElementMessageProducer;
import dh.distributed.systems.Server.kafka.producer.ListMessageProducer;
import dh.distributed.systems.Server.message.ElementMessage;
import dh.distributed.systems.Server.message.ElementMessageAnswer;
import dh.distributed.systems.Server.message.ListMessage;
import dh.distributed.systems.Server.message.ListMessageAnswer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class WSListMessageController {

    private final ElementMessageProducer elementProducer;

    private final ListMessageProducer listProducer;

    @MessageMapping("/list")
    @SendTo("/topic/list-answer")
    public ListMessageAnswer listMessage(ListMessage message) throws Exception {
        listProducer.sendMessage("list", message);
        return new ListMessageAnswer(
                "Message sent, " 
                + "userID=" + message.userID() 
                + ",listID=" + message.listID()
                + ",action=" + message.action() 
                + ",list:title=" + message.list().getTitle() 
                + ",favorite=" + message.list().getFavorite());
    }

    @MessageMapping("/element")
    @SendTo("/topic/element-answer")
    public ElementMessageAnswer elementMessage(ElementMessage message) throws Exception {
        elementProducer.sendMessage("listelement", message);
        return new ElementMessageAnswer(
                "Message sent, "
                + "userID=" + message.userID()
                + ",listID=" + message.listID()
                + ",elementID=" + message.elementID()
                + ",action=" + message.action()
                + ",element:name=" + message.element().getName()
                + ",status=" + message.element().getStatus()
                + ",priority=" + message.element().getPriority().toString()
                + ",tags=" + message.element().getTags().toString()
                + ",dueDate=" + message.element().getDueDate().toString());
    }
}
