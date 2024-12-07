package dh.distributed.systems.Server.message;

import java.util.List;

public record ListAnswer(Integer listID, TodoListResponse list, List<TodoListResponse> lists, Boolean result) {}
