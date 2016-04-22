package mti.commons.json.job;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import mti.commons.model.MtiEnumerations.SensorType;

public class SensorTypeJsonDeserializer extends JsonDeserializer<SensorType> {

    @Override
    public SensorType deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException
    {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return SensorType.getSensorType(Short.parseShort(root.get("enum").asText()));
    }
}
