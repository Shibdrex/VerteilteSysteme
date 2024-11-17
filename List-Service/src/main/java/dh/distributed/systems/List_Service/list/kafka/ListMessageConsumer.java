package dh.distributed.systems.List_Service.list.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import dh.distributed.systems.List_Service.list.manager.TodoListManager;
import dh.distributed.systems.List_Service.list.model.ListMessage;
import dh.distributed.systems.List_Service.list.model.TodoList;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ListMessageConsumer {
    
    private final TodoListManager manager;

    @KafkaListener(
        topics = "list", 
        groupId = "list-group", 
        clientIdPrefix = "list-consumer", 
        containerFactory = "kafkaListMessageListenerContainerFactory")
    public void listen(ListMessage message) {
        TodoList list;
        switch (message.action()) {
            case "CREATE":
                list = this.manager.createList(message.userID(), message.list());
                // TODO: Maybe send a message back to frontend, informing about result
                break;
            case "UPDATE":
                list = this.manager.updateList(message.list(), message.listID());
                // TODO: Maybe send a message back to frontend, informing about result
                break;
            case "DELETE":
                this.manager.deleteList(message.listID());
                // TODO: Maybe send a message back to frontend, informing about result
                break;
            case "DELETE_BY_USER":
                this.manager.deleteAllByUser(message.userID());
                // TODO: Maybe send a message back to frontend, informing about result
                break;
            default:
                // TODO: Maybe send a message back to frontend, informing about unrecognized method
                break;
        }
    }
}