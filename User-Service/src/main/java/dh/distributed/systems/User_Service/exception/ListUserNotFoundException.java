package dh.distributed.systems.User_Service.exception;

public class ListUserNotFoundException extends RuntimeException {

    public ListUserNotFoundException(Integer id) {
        super("Could not find List-User [" + id + "]");
    }
}
