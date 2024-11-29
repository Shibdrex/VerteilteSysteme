package dh.distributed.systems.List_Service.list.kafka;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import dh.distributed.systems.List_Service.list.model.ListMessage;

public class ListMessageDeSerializer implements Deserializer<ListMessage> {
    
    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public ListMessage deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, ListMessage.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
