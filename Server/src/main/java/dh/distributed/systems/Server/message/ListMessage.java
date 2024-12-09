package dh.distributed.systems.Server.message;

import java.util.UUID;

import dh.distributed.systems.Server.model.TodoList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class represents a dto of a message that is send to the List-Service.
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