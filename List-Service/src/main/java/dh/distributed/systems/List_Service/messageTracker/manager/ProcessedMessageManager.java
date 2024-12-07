package dh.distributed.systems.List_Service.messageTracker.manager;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.messageTracker.exception.ProcessedMessageNotFoundException;
import dh.distributed.systems.List_Service.messageTracker.model.ProcessedMessage;
import dh.distributed.systems.List_Service.messageTracker.repository.ProcessedMessageRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProcessedMessageManager {
    
    private final ProcessedMessageRepository repository;

    public Boolean isValid(final ProcessedMessage message) {
        return message != null
                && message.getMessageId() != null
                && message.getUserId() != null
                && message.getAction() != null;
    }

    public List<ProcessedMessage> getAllProcessedMessages() {
        return this.repository.findAll();
    }

    public ProcessedMessage getProcessedMessage(final Integer id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ProcessedMessageNotFoundException(id));
    }

    public ProcessedMessage findByMessageId(final UUID id) {
        return this.repository.findByMessageId(id).orElse(null);
    }

    public ProcessedMessage creatProcessedMessage(final ProcessedMessage message) {
        if (!isValid(message)) {
            throw new IllegalArgumentException("Invalid processed message data.");
        }
        return this.repository.save(message);
    }

    public ProcessedMessage updateProcessedMessage(final ProcessedMessage newMessage, final Integer id) {
        if (!isValid(newMessage)) {
            throw new IllegalArgumentException("Invalid processed message data.");
        }
        return this.repository.findById(id)
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

    public void deleteProcessedMessage(final Integer id) {
        ProcessedMessage message = this.repository.findById(id)
                .orElseThrow(() -> new ProcessedMessageNotFoundException(id));
        this.repository.delete(message);
    }

    public void deleteAll() {
        this.repository.deleteAll();
    }
}
