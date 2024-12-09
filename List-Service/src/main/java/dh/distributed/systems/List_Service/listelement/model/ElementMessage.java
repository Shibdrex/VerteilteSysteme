package dh.distributed.systems.List_Service.listelement.model;

import java.util.UUID;

import dh.distributed.systems.List_Service.TodoMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class represents a dto of a message that is received coming from the server.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ElementMessage extends TodoMessage {
    private Integer elementID;
    private ListElement element;

    public ElementMessage() {
        super();
    }

    public ElementMessage(UUID messageID, Integer userID, Integer listID, String action, Integer elementID,
            ListElement element) {
        super(messageID, userID, listID, action);
        this.elementID = elementID;
        this.element = element;
    }

    public ElementMessage(UUID messageID, Integer userID, Integer listID, String action, Integer elementID) {
        super(messageID, userID, listID, action);
        this.elementID = elementID;
    }

    public ElementMessage(UUID messageID, Integer userID, Integer listID, String action, ListElement element) {
        super(messageID, userID, listID, action);
        this.element = element;
    }

    public ElementMessage(UUID messageID, Integer userID, Integer listID, String action) {
        super(messageID, userID, listID, action);
    }

    public ElementMessage(UUID messageID, Integer userID, String action, Integer elementID) {
        super(messageID, userID, action);
        this.elementID = elementID;
    }

    public ElementMessage(UUID messageID, Integer userID, String action, ListElement element) {
        super(messageID, userID, action);
        this.element = element;
    }

    public ElementMessage(UUID messageID, Integer userID, String action) {
        super(messageID, userID, action);
    }
}
