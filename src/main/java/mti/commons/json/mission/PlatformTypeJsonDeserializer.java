package mti.commons.json.mission;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import mti.commons.model.MtiEnumerations.PlatformType;

public class PlatformTypeJsonDeserializer extends JsonDeserializer<PlatformType> {

    @Override
    public PlatformType deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException
    {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return PlatformType.getPlatformType(Short.parseShort(root.get("enum").asText()));
    }
}
