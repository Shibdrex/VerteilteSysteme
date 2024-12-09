package dh.distributed.systems.List_Service.messageTracker.manager;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.messageTracker.exception.ProcessedMessageNotFoundException;
import dh.distributed.systems.List_Service.messageTracker.model.ProcessedMessage;
import dh.distributed.systems.List_Service.messageTracker.repository.ProcessedMessageRepository;
import lombok.AllArgsConstructor;

/**
 * Handles database transactions of the processed message table.
 */
@AllArgsConstructor
@Service
public class ProcessedMessageManager {

    private final ProcessedMessageRepository repository;

    /**
     * Method to check if certain fields are filled.
     * 
     * @param message {@link ProcessedMessage} object to check
     * @return true if and only if messageID, userID and action fields are not null,
     *         false otherwise
     */
    public Boolean isValid(final ProcessedMessage message) {
        return message != null
                && message.getMessageId() != null
                && message.getUserId() != null
                && message.getAction() != null;
    }

    /**
     * Method to retrieve all processed messages.
     * 
     * @return a list of all processed messages
     */
    public List<ProcessedMessage> getAllProcessedMessages() {
        return this.repository.findAll();
    }

    /**
     * Method to retrieve a specific processed message with the given ID, if no
     * processed Message is found with the ID an exception is thrown.
     * 
     * @param ID of the processed message to look for
     * @return the processed message with the ID
     */
    public ProcessedMessage getProcessedMessage(final Integer ID) {
        return this.repository.findById(ID)
                .orElseThrow(() -> new ProcessedMessageNotFoundException(ID));
    }

    /**
     * Method to search the processed message with the given UUID, if no processed
     * message is found with the UUID an exception is thrown.
     * 
     * @param UUID to look for in the database table
     * @return the processed message with the UUID
     */
    public ProcessedMessage findByMessageId(final UUID UUID) {
        return this.repository.findByMessageId(UUID).orElse(null);
    }

    /**
     * Method to create a new processed message and add it to th database table.
     * First checks that data of given processed message is valid and not empty.
     * 
     * @param message object to create
     * @return the created processed message object
     */
    public ProcessedMessage createProcessedMessage(final ProcessedMessage message) {
        if (!isValid(message)) {
            throw new IllegalArgumentException("Invalid processed message data.");
        }
        return this.repository.save(message);
    }

    /**
     * Method to update a specific processed message in the database. First checks
     * that data of given processed message is valid and not empty. If no processed
     * message with the ID exists a new processed message is created instead with
     * the given data.
     * 
     * @param newMessage object containing data to update
     * @param ID         of the processed message to update
     * @return the updated or created processed message object
     */
    public ProcessedMessage updateProcessedMessage(final ProcessedMessage newMessage, final Integer ID) {
        if (!isValid(newMessage)) {
            throw new IllegalArgumentException("Invalid processed message data.");
        }
        return this.repository.findById(ID)
                .map(user -> {
                    user.setMessageId(newMessage.getMessageId());
                    user.setUserId(newMessage.getUserId());
                    user.setAction(newMessage.getAction());
                    user.setListId(newMessage.getListId());
                    user.setTitle(newMessage.getTitle());
                    user.setFavorite(newMessage.getFavorite());
                    user.setElementId(newMessage.getElementId());
                    user.setStatus(newMessage.getStatus());
                    user.setPriority(newMessage.getPriority());
                    user.setTags(newMessage.getTags());
                    user.setDueDate(newMessage.getDueDate());
                    user.setName(newMessage.getName());
                    return this.repository.save(user);
                })
                .orElseGet(() -> this.repository.save(newMessage));
    }

    /**
     * Method to delete a processed message with the specified ID, if no processed
     * message with the ID is found throws an exception.
     * 
     * @param ID of the processed message to delete
     */
    public void deleteProcessedMessage(final Integer ID) {
        ProcessedMessage message = this.repository.findById(ID)
                .orElseThrow(() -> new ProcessedMessageNotFoundException(ID));
        this.repository.delete(message);
    }

    /**
     * Method to delete all processed messages
     */
    public void deleteAll() {
        this.repository.deleteAll();
    }
}
