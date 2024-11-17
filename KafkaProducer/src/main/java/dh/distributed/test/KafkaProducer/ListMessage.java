package dh.distributed.test.KafkaProducer;

public record ListMessage(Integer userID, Integer listID, String action, TodoList list) {
    
}

