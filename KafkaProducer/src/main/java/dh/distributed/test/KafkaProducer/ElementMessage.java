package dh.distributed.test.KafkaProducer;

public record ElementMessage(Integer userID, Integer listID, Integer elementID, String action, ListElement element) {
}
