package dh.distributed.systems.Server.message;

import java.util.List;

/**
 * Class represents a dto of a message that is received from the List-Service.
 */
public record ListAnswer(Integer listID, TodoListResponse list, List<TodoListResponse> lists, Boolean result) {}
