package dh.distributed.systems.List_Service.messageTracker.tracker;

import org.springframework.stereotype.Service;

import dh.distributed.systems.List_Service.TodoMessage;
import dh.distributed.systems.List_Service.list.model.ListMessage;
import dh.distributed.systems.List_Service.listelement.model.ElementMessage;
import dh.distributed.systems.List_Service.messageTracker.manager.ProcessedMessageManager;
import dh.distributed.systems.List_Service.messageTracker.model.ProcessedMessage;
import lombok.AllArgsConstructor;

/**
 * Offers methods to check and mark messages.
 */
@AllArgsConstructor
@Service
public class ProcessedMessageTracker {

    private final ProcessedMessageManager manager;

    public Boolean isProcessed(TodoMessage message) {
        if (message instanceof ElementMessage elementMessage) {
            if (this.manager.findByMessageId(elementMessage.getMessageID()) != null) {
                return true;
            }
        }
        if (message instanceof ListMessage listMessage) {
            if (this.manager.findByMessageId(listMessage.getMessageID()) != null) {
                return true;
            }
        }
        return false;
    }

    public void markProcessed(TodoMessage message) {
        if (message instanceof ElementMessage elementMessage) {
            this.manager.createProcessedMessage(new ProcessedMessage(elementMessage));
        }
        if (message instanceof ListMessage listMessage) {
            this.manager.createProcessedMessage(new ProcessedMessage(listMessage));
        }
    }
}
