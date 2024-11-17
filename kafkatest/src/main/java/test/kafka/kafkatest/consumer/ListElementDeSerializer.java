package test.kafka.kafkatest.consumer;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

public class ListElementDeSerializer implements Deserializer<ListElement> {

    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public ListElement deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, ListElement.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
