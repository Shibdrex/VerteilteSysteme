package dh.distributed.systems.List_Service.listUser.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dh.distributed.systems.List_Service.listUser.exception.ListUserNotFoundException;

@RestControllerAdvice
public class ListUserNotFoundAdvice {

    @ExceptionHandler(ListUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String listUserNotFoundHandler(ListUserNotFoundException ex) {
        return ex.getMessage();
    }
}
