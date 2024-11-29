package dh.distributed.systems.Server.message;

import dh.distributed.systems.Server.model.ListElement;

public record ElementMessage(Integer userID, Integer listID, Integer elementID, String action, ListElement element) {
}
