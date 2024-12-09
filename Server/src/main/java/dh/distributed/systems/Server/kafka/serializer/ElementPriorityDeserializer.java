package dh.distributed.systems.Server.kafka.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import dh.distributed.systems.Server.model.ElementPriority;

public class ElementPriorityDeserializer extends StdDeserializer<ElementPriority> {
    
    public ElementPriorityDeserializer() {
        super(ElementPriority.class);
    }

    @Override
    public ElementPriority deserialize(JsonParser p, DeserializationContext context) throws IOException {
        String value = p.getText();
        if (value == null || value.isEmpty()) {
            return ElementPriority.MEDIUM;
        }
        return ElementPriority.valueOf(value.toUpperCase());
    }
}
