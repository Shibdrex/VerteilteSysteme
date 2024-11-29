package dh.distributed.systems.List_Service.listelement.model;

public record ElementMessage(Integer userID, Integer listID, Integer elementID, String action, ListElement element) {
}
