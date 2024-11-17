package dh.distributed.systems.List_Service.list.model;

public record ListMessage(Integer userID, Integer listID, String action, TodoList list) {
    
}
