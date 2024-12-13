package dh.distributed.systems.List_Service.list.exception;

public class TodoListNotFoundException extends RuntimeException{
    
    public TodoListNotFoundException(Integer ID) {
        super("Could not find Todo-List [" + ID + "]");
    }
}
