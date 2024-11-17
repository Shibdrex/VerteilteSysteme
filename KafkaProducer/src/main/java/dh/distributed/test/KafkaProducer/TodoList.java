package dh.distributed.test.KafkaProducer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoList {

    private String title;
    private Boolean favorite;
}
