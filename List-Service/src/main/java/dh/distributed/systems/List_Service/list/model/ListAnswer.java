package dh.distributed.systems.List_Service.list.model;

import java.util.List;

import dh.distributed.systems.List_Service.list.dto.TodoListResponse;

/**
 * Record that represents a dto of a message to be send to server
 */
public record ListAnswer(Integer listID, TodoListResponse list, List<TodoListResponse> lists, Boolean result) {}
