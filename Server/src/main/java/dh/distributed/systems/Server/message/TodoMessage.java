package dh.distributed.systems.Server.message;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class represents a basic message, that is used for kafka messaging.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TodoMessage {
    private UUID messageID;
    private Integer userID;
    private Integer listID;
    private String action;

    public TodoMessage(UUID messageID, Integer userID, String action) {
        this.messageID = messageID;
        this.userID = userID;
        this.action = action;
    }
}
