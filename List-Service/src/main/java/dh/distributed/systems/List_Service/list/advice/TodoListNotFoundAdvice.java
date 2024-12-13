package dh.distributed.systems.List_Service.list.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dh.distributed.systems.List_Service.list.exception.TodoListNotFoundException;

@RestControllerAdvice
public class TodoListNotFoundAdvice {

    @ExceptionHandler(TodoListNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String todoListNotFoundHandler(TodoListNotFoundException ex) {
        return ex.getMessage();
    }
}