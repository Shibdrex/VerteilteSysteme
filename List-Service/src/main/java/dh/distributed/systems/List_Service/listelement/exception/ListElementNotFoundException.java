package dh.distributed.systems.List_Service.listelement.exception;

public class ListElementNotFoundException extends RuntimeException {

    public ListElementNotFoundException(Integer ID) {
        super("Could not find List-Element [" + ID + "]");
    }
}
