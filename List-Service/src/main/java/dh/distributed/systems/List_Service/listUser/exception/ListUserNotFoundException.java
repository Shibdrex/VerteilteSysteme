package dh.distributed.systems.List_Service.listUser.exception;

public class ListUserNotFoundException extends RuntimeException {

    public ListUserNotFoundException(Integer id) {
        super("Could not find List-User [" + id + "]");
    }
}
