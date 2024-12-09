package dh.distributed.systems.Server.message;

import java.util.List;

/**
 * Class represents a dto of a message that is received from the List-Service.
 */
public record ElementAnswer(Integer elementID, ListElementResponse element, List<ListElementResponse> elements,
        Boolean result) {
}
