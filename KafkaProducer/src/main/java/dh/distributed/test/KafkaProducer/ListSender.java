package dh.distributed.test.KafkaProducer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ListSender {

    private final ListMessageProducer producer;

    //@Scheduled(fixedRate = 5000)
    public void keepSending() throws InterruptedException {
        TodoList list = new TodoList("Tolle liste", false);
        ListMessage message = new ListMessage(0, null, "CREATE", list);
        this.producer.sendMessage("list", message);
    }
}
