package dh.distributed.systems.List_Service.listelement.kafka.producer;

import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.cfg.CoercionAction;
import com.fasterxml.jackson.databind.cfg.CoercionInputShape;
import com.fasterxml.jackson.databind.json.JsonMapper;

import dh.distributed.systems.List_Service.listelement.model.ElementAnswer;

public class ElementAnswerSerializer implements Serializer<ElementAnswer> {

    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    static {
        mapper.coercionConfigFor(Enum.class)
                .setCoercion(CoercionInputShape.EmptyString, CoercionAction.AsNull);
    }
    @Override
    public byte[] serialize(String topic, ElementAnswer answer) {
        try {
            return mapper.writeValueAsBytes(answer);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }
    }
}
