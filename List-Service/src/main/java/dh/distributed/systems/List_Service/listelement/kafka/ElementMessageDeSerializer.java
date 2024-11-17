package dh.distributed.systems.List_Service.listelement.kafka;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import dh.distributed.systems.List_Service.listelement.model.ElementMessage;

public class ElementMessageDeSerializer implements Deserializer<ElementMessage> {

    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public ElementMessage deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, ElementMessage.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}