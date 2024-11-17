package dh.distributed.systems.List_Service.listelement.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import dh.distributed.systems.List_Service.listelement.manager.ListElementManager;
import dh.distributed.systems.List_Service.listelement.model.ElementMessage;
import dh.distributed.systems.List_Service.listelement.model.ListElement;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ElementMessageConsumer {

    private final ListElementManager manager;

    @KafkaListener(
        topics = "listelement", 
        groupId = "list-group", 
        clientIdPrefix = "listelement-consumer", 
        containerFactory = "kafkaElementMessageListenerContainerFactory")
    public void listen(ElementMessage message) {
        ListElement element;
        switch (message.action()) {
            case "CREATE":
                element = this.manager.createElement(message.userID(), message.listID(), message.element());
                // TODO: Maybe send a message back to frontend, informing about result
                break;
            case "UPDATE":
                element = this.manager.updateElement(message.element(), message.elementID());
                // TODO: Maybe send a message back to frontend, informing about result
                break;
            case "DELETE":
                this.manager.deleteElement(message.elementID());
                // TODO: Maybe send a message back to frontend, informing about result
                break;
            case "DELETE_BY_USER":
                this.manager.deleteAllByUser(message.userID());
                // TODO: Maybe send a message back to frontend, informing about result
                break;
            case "DELETE_BY_LIST":
                this.manager.deleteAllByList(message.listID());
                break;
            default:
                // TODO: Maybe send a message back to frontend, informing about unrecognized method
                break;
        }
    }


}
