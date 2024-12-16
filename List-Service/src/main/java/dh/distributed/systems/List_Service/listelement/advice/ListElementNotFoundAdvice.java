package dh.distributed.systems.List_Service.listelement.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dh.distributed.systems.List_Service.listelement.exception.ListElementNotFoundException;

@RestControllerAdvice
public class ListElementNotFoundAdvice {

    @ExceptionHandler(ListElementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String listElementNotFoundHandler(ListElementNotFoundException ex) {
        return ex.getMessage();
    }
}
