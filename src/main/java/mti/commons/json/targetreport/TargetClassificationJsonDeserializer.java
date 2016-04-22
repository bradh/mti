package mti.commons.json.targetreport;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import mti.commons.model.MtiEnumerations.TargetClassification;

public class TargetClassificationJsonDeserializer extends JsonDeserializer<TargetClassification> {

    @Override
    public TargetClassification deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException
    {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return TargetClassification.getTargetClassification(Short.parseShort(root.get("enum").asText()));
    }
}
