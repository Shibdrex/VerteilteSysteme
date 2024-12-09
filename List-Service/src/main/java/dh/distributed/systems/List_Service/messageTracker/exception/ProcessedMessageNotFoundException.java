package dh.distributed.systems.List_Service.messageTracker.exception;

public class ProcessedMessageNotFoundException extends RuntimeException {
    
    public ProcessedMessageNotFoundException(Integer id) {
        super("Could not find Processed-Message [" + id + "]");
    }
}
