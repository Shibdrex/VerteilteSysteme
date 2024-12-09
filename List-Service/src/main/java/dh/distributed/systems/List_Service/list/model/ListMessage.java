package dh.distributed.systems.List_Service.list.model;

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
public class ListMessage extends TodoMessage {
    private TodoList list;

    public ListMessage() {
        super();
    }

    public ListMessage(UUID messageID, Integer userID, Integer listID, String action, TodoList list) {
        super(messageID, userID, listID, action);
        this.list = list;
    }

    public ListMessage(UUID messageID, Integer userID, Integer listID, String action) {
        super(messageID, userID, listID, action);
    }

    public ListMessage(UUID messageID, Integer userID, String action, TodoList list) {
        super(messageID, userID, action);
        this.list = list;
    }

    public ListMessage(UUID messageID, Integer userID, String action) {
        super(messageID, userID, action);
    }
}