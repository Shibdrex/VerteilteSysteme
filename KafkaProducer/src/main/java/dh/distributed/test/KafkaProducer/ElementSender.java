package dh.distributed.test.KafkaProducer;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ElementSender {

    private final ElementMessageProducer producer;

    //@Scheduled(fixedRate = 5000)
    public void keepSending() throws InterruptedException {
        Set<String> tags = new HashSet<>();
        tags.add("Mono");
        tags.add("Di");
        tags.add("Tri");
        tags.add("Tetra");
        ListElement element = new ListElement(
                true,
                ElementPriority.MEDIUM,
                tags,
                new Date(0),
                "Mega");
        ElementMessage message = new ElementMessage(0, 0, null, "CREATE", element);
        this.producer.sendMessage("elementMessage", message);
    }
}
