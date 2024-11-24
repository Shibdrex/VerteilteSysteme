package dh.distributed.systems.Server.kafka.serializer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import dh.distributed.systems.Server.message.ListMessage;

public class ListMessageSerializer implements Serializer<ListMessage> {

    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    
    @Override
    public byte[] serialize(String topic, ListMessage message) {
        try {
            return mapper.writeValueAsBytes(message);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
