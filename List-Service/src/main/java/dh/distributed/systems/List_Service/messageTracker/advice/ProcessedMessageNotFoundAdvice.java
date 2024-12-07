package dh.distributed.systems.List_Service.messageTracker.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dh.distributed.systems.List_Service.messageTracker.exception.ProcessedMessageNotFoundException;

@RestControllerAdvice
public class ProcessedMessageNotFoundAdvice {
    
    @ExceptionHandler(ProcessedMessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String processedMessageNotFoundHandler(ProcessedMessageNotFoundException ex) {
        return ex.getMessage();
    }
}
