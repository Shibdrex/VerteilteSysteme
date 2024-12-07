package dh.distributed.systems.List_Service.list.model;

import java.util.List;

import dh.distributed.systems.List_Service.list.dto.TodoListResponse;

public record ListAnswer(Integer listID, TodoListResponse list, List<TodoListResponse> lists, Boolean result) {}
