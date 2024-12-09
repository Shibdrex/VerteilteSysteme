package dh.distributed.systems.List_Service.list.kafka.consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import dh.distributed.systems.List_Service.ListServiceApplication;
import dh.distributed.systems.List_Service.list.dto.TodoListResponse;
import dh.distributed.systems.List_Service.list.kafka.producer.ListAnswerProducer;
import dh.distributed.systems.List_Service.list.manager.TodoListManager;
import dh.distributed.systems.List_Service.list.model.ListAnswer;
import dh.distributed.systems.List_Service.list.model.ListMessage;
import dh.distributed.systems.List_Service.list.model.TodoList;
import dh.distributed.systems.List_Service.messageTracker.tracker.ProcessedMessageTracker;
import lombok.AllArgsConstructor;

/**
 * Class to deal with incoming {@link ListMessage}s from kafka. Executes
 * database actions based on the message received. Tracks messages if the
 * database action was successful and if a message that has been added to the
 * tracking database table is received again the action will not be executed.
 */
@Component
@AllArgsConstructor
public class ListMessageConsumer {

    private final TodoListManager manager;
    private final ProcessedMessageTracker tracker;
    private final ListAnswerProducer producer;

    private static final Logger log = LoggerFactory.getLogger(ListServiceApplication.class);

    @KafkaListener(topics = "todo-list-send", containerFactory = "kafkaListMessageListenerContainerFactory")
    public void listen(ListMessage message) {
        if (this.tracker.isProcessed(message)) { // message has already been processed
            producer.sendMessage("todo-list-answer", null);
            return; // send empty message to server and stop further processing
        }
        try {
            TodoList list;
            TodoListResponse response;
            List<TodoList> lists;
            List<TodoListResponse> responses;
            switch (message.getAction()) {
                case "GET_ONE":
                    list = this.manager.getList(message.getListID());
                    response = new TodoListResponse(list);
                    producer.sendMessage("todo-list-answer", new ListAnswer(list.getId(), response, null, true));
                    break;
                case "GET_ALL":
                    lists = this.manager.getAllLists();
                    responses = lists.stream().map(TodoListResponse::new).collect(Collectors.toList());
                    producer.sendMessage("todo-list-answer", new ListAnswer(null, null, responses, true));
                    break;
                case "GET_ALL_BY_USER":
                    lists = this.manager.getAllListsByUserID(message.getUserID());
                    responses = lists.stream().map(TodoListResponse::new).collect(Collectors.toList());
                    producer.sendMessage("todo-list-answer", new ListAnswer(null, null, responses, true));
                    break;
                case "CREATE":
                    list = this.manager.createList(message.getUserID(), message.getList());
                    response = new TodoListResponse(list);
                    producer.sendMessage("todo-list-answer", new ListAnswer(list.getId(), response, null, true));
                    break;
                case "UPDATE":
                    list = this.manager.updateList(message.getList(), message.getListID());
                    response = new TodoListResponse(list);
                    producer.sendMessage("todo-list-answer", new ListAnswer(list.getId(), response, null, true));
                    break;
                case "DELETE":
                    this.manager.deleteList(message.getListID());
                    producer.sendMessage("todo-list-answer", new ListAnswer(null, null, null, true));
                    break;
                case "DELETE_BY_USER":
                    this.manager.deleteAllByUser(message.getUserID());
                    producer.sendMessage("todo-list-answer", new ListAnswer(null, null, null, true));
                    break;
                case "DELETE_ALL":
                    this.manager.deleteAll();
                    producer.sendMessage("todo-list-answer", new ListAnswer(null, null, null, true));
                    break;
                default: // if no action was given or action is invalid, send message to server informing about this
                    producer.sendMessage("todo-list-answer", new ListAnswer(null, null, null, false));
                    break;
            }
        } catch (Exception ex) {
            log.info("Something went wrong: " + ex);
        }
        this.tracker.markProcessed(message); // adds the just processed message to the tracking table
    }
}