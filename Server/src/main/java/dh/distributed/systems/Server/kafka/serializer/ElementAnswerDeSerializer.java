package dh.distributed.systems.Server.kafka.serializer;

import java.io.IOException;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.json.JsonMapper;

import dh.distributed.systems.Server.message.ElementAnswer;

public class ElementAnswerDeSerializer implements Deserializer<ElementAnswer> {

    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();


    static {
        mapper.coercionConfigFor(Enum.class)
                .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
    }
    @Override
    public ElementAnswer deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, ElementAnswer.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}