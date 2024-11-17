package dh.distributed.test.KafkaProducer;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("/kafka")
public class DoActionController {

    private final ListMessageProducer listProducer;

    private final ElementMessageProducer elementProducer;


    @PostMapping("/list")
    public String sendListMessage(@RequestBody ListMessage message) {

        this.listProducer.sendMessage("list", message);

        return "List message sent";
    }

    @PostMapping("/element")
    public String sendElementMessage(@RequestBody ElementMessage message) {

        this.elementProducer.sendMessage("listelement", message);

        return "Element message sent";
    }
}
