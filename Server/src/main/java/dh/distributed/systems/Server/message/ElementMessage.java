package dh.distributed.systems.Server.message;

import java.util.UUID;

import dh.distributed.systems.Server.model.ListElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ElementMessage extends TodoMessage {
    private Integer elementID;
    private ListElement element;

    public ElementMessage() {super();}

    public ElementMessage(UUID messageID, Integer userID, Integer listID, String action, Integer elementID, ListElement element) {
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


