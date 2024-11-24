package dh.distributed.systems.Server.message;

import dh.distributed.systems.Server.model.TodoList;

public record ListMessage(Integer userID, Integer listID, String action, TodoList list) {
    
}

