package dh.distributed.systems.List_Service.list.kafka.consumer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.json.JsonMapper;

import dh.distributed.systems.List_Service.ListServiceApplication;
import dh.distributed.systems.List_Service.list.model.ListMessage;

public class ListMessageDeSerializer implements Deserializer<ListMessage> {

    private static final Logger log = LoggerFactory.getLogger(ListServiceApplication.class);
    
    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    static {
        mapper.coercionConfigFor(Enum.class) // if a enum field is empty string replace with null
                .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
    }
    @Override
    public ListMessage deserialize(String topic, byte[] data) {
        try {
            String inputString = new String(data, StandardCharsets.UTF_8);
            log.info(inputString);
            return mapper.readValue(data, ListMessage.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}
