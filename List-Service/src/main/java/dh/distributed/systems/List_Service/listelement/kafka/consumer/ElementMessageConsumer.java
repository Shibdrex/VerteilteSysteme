package dh.distributed.systems.List_Service.listelement.kafka.consumer;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import dh.distributed.systems.List_Service.ListServiceApplication;
import dh.distributed.systems.List_Service.listelement.dto.ListElementResponse;
import dh.distributed.systems.List_Service.listelement.kafka.producer.ElementAnswerProducer;
import dh.distributed.systems.List_Service.listelement.manager.ListElementManager;
import dh.distributed.systems.List_Service.listelement.model.ElementAnswer;
import dh.distributed.systems.List_Service.listelement.model.ElementMessage;
import dh.distributed.systems.List_Service.listelement.model.ListElement;
import dh.distributed.systems.List_Service.messageTracker.tracker.ProcessedMessageTracker;
import lombok.AllArgsConstructor;

/**
 * Class to deal with incoming {@link ElementMessage}s from kafka. Executes
 * database actions based on the message received. Tracks messages if the
 * database action was successful and if a message that has been added to th
 * tracking database table is received again the action will not be executed.
 */
@Component
@AllArgsConstructor
public class ElementMessageConsumer {

    private final ListElementManager manager;
    private final ProcessedMessageTracker tracker;
    private final ElementAnswerProducer producer;

    private static final Logger log = LoggerFactory.getLogger(ListServiceApplication.class);

    @KafkaListener(topics = "todo-element-send", containerFactory = "kafkaElementMessageListenerContainerFactory")
    public void listen(ElementMessage message) {
        if (this.tracker.isProcessed(message)) { // message has already been processed
            producer.sendMessage("todo-element-answer", null);
            return; // send empty message to server and stop further processing
        }
        try {
            ListElement element;
            ListElementResponse response;
            List<ListElement> elements;
            List<ListElementResponse> responses;
            switch (message.getAction()) {
                case "GET_ONE":
                    element = this.manager.getElement(message.getElementID());
                    response = new ListElementResponse(element);
                    producer.sendMessage("todo-element-answer",
                            new ElementAnswer(element.getId(), response, null, true));
                    break;
                case "GET_ALL":
                    elements = this.manager.getAllElements();
                    responses = elements.stream().map(ListElementResponse::new).collect(Collectors.toList());
                    producer.sendMessage("todo-element-answer", new ElementAnswer(null, null, responses, true));
                    break;
                case "GET_ALL_BY_USER":
                    elements = this.manager.getAllElementsByUserID(message.getUserID());
                    responses = elements.stream().map(ListElementResponse::new).collect(Collectors.toList());
                    producer.sendMessage("todo-element-answer", new ElementAnswer(null, null, responses, true));
                    break;
                case "GET_ALL_BY_LIST":
                    elements = this.manager.getAllElementsByListID(message.getListID());
                    responses = elements.stream().map(ListElementResponse::new).collect(Collectors.toList());
                    producer.sendMessage("todo-element-answer", new ElementAnswer(null, null, responses, true));
                    break;
                case "CREATE":
                    element = this.manager.createElement(message.getUserID(), message.getListID(),
                            message.getElement());
                    response = new ListElementResponse(element);
                    producer.sendMessage("todo-element-answer",
                            new ElementAnswer(element.getId(), response, null, true));
                    break;
                case "UPDATE":
                    element = this.manager.updateElement(message.getElement(), message.getElementID());
                    response = new ListElementResponse(element);
                    producer.sendMessage("todo-element-answer",
                            new ElementAnswer(element.getId(), response, null, true));
                    break;
                case "DELETE":
                    this.manager.deleteElement(message.getElementID());
                    producer.sendMessage("todo-element-answer", new ElementAnswer(null, null, null, true));
                    break;
                case "DELETE_BY_USER":
                    this.manager.deleteAllByUser(message.getUserID());
                    producer.sendMessage("todo-element-answer", new ElementAnswer(null, null, null, true));
                    break;
                case "DELETE_BY_LIST":
                    this.manager.deleteAllByList(message.getListID());
                    producer.sendMessage("todo-element-answer", new ElementAnswer(null, null, null, true));
                    break;
                case "DELETE_ALL":
                    this.manager.deleteAll();
                    producer.sendMessage("todo-element-answer", new ElementAnswer(null, null, null, true));
                    break;
                default: // if no action was given or action is invalid, send message to server informing
                         // about this
                    producer.sendMessage("todo-element-answer", new ElementAnswer(null, null, null, false));
                    break;
            }
        } catch (Exception ex) {
            log.info("Something went wrong: " + ex);
        }
        this.tracker.markProcessed(message); // adds the just processed message to the tracking table
    }

}
