package mti.commons.json.job;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import mti.commons.model.MtiEnumerations.RadarMode;

public class RadarModeJsonDeserializer extends JsonDeserializer<RadarMode> {

    @Override
    public RadarMode deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException
    {
        ObjectCodec oc = jp.getCodec();
        JsonNode root = oc.readTree(jp);
        return RadarMode.getRadarMode(Short.parseShort(root.get("enum").asText()));
    }
}
